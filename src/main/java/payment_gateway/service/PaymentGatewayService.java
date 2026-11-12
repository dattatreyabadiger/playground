package payment_gateway.service;

import payment_gateway.model.PaymentInfo;
import payment_gateway.model.PaymentStatus;
import payment_gateway.model.ProviderResult;
import payment_gateway.payment_providers.PaymentProvider;
import payment_gateway.model.PaymentRequest;
import payment_gateway.repository.PaymentProviderRepository;
import payment_gateway.repository.PaymentRepository;

import java.util.Set;

public class PaymentGatewayService {

    private final PaymentRepository paymentRepository;
    private final PaymentProviderRepository paymentProviderRepository;


    public PaymentGatewayService(PaymentRepository paymentRepository,
                                 PaymentProviderRepository paymentProviderRepository) {
        this.paymentRepository = paymentRepository;
        this.paymentProviderRepository = paymentProviderRepository;
    }


    public int makePayment(PaymentRequest paymentRequest) {
        int orderId = paymentRequest.getOrderId();
        int idempotencyKey = paymentRequest.getIdempotentKey();
        PaymentInfo existingAttempt = paymentRepository.findByOrderIdAndKey(orderId, idempotencyKey);
        if (existingAttempt != null)
            // return the payment ID of the previous attempt
            return existingAttempt.getPaymentId();


        PaymentInfo latestPaymentInfo = paymentRepository.findByOrderId(orderId);
        if (latestPaymentInfo != null &&
                (latestPaymentInfo.getPaymentStatus() == PaymentStatus.SUCCESS ||
                        latestPaymentInfo.getPaymentStatus() == PaymentStatus.UNKNOWN))
            return latestPaymentInfo.getPaymentId();


        Set<PaymentProvider> supportedPaymentProvider
                = paymentProviderRepository.getSupportedPaymentMethods(paymentRequest.getPaymentMethod());
        ProviderResult providerResult = ProviderResult.DECLINED;
        for (PaymentProvider paymentProvider : supportedPaymentProvider) {
            providerResult = paymentProvider.charge(paymentRequest.getAmount());
            if (providerResult != ProviderResult.DECLINED)
                break;
        }

        return paymentRepository.save(paymentRequest, getPaymentStatusFor(providerResult));
    }

    public PaymentInfo getPaymentInfo(int paymentId) {
        PaymentInfo paymentInfo = paymentRepository.findByPaymentId(paymentId);
        if (paymentInfo == null)
            throw new IllegalArgumentException("Payment Details do not exist for ID = " + paymentId);
        return paymentInfo;
    }

    private PaymentStatus getPaymentStatusFor(ProviderResult providerResult) {
        switch (providerResult) {
            case SUCCESS -> {
                return PaymentStatus.SUCCESS;
            }
            case DECLINED -> {
                return PaymentStatus.FAILED;
            }
            case TIMED_OUT -> {
                return PaymentStatus.UNKNOWN;
            }
            default -> {
                return null;
            }
        }
    }

}
