package payment_gateway.repository;

import payment_gateway.model.PaymentInfo;
import payment_gateway.model.PaymentMethod;
import payment_gateway.model.PaymentRequest;
import payment_gateway.model.PaymentStatus;
import payment_gateway.payment_providers.PaymentProvider;

import java.util.Set;

public interface PaymentProviderRepository {

    Set<PaymentProvider> getSupportedPaymentMethods(PaymentMethod paymentMethod);
}
