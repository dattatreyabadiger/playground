package payment_gateway.repository;

import payment_gateway.model.PaymentInfo;
import payment_gateway.model.PaymentRequest;
import payment_gateway.model.PaymentStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryPaymentRepository implements PaymentRepository {

    private final Map<Integer, Map<Integer, Integer>> orderIdToAttemptsInfo;
    private final Map<Integer, Integer> orderIdToPaymentInfoMap;
    private final Map<Integer, PaymentInfo> paymentIdToPaymentInfoMap;

    private final AtomicInteger paymentIdGenerator = new AtomicInteger();

    public InMemoryPaymentRepository() {
        this.orderIdToAttemptsInfo = new HashMap<>();
        this.orderIdToPaymentInfoMap = new HashMap<>();
        this.paymentIdToPaymentInfoMap = new HashMap<>();
    }

    @Override
    public PaymentInfo findByOrderId(int orderId) {
        Integer paymentId = orderIdToPaymentInfoMap.get(orderId);
        if (paymentId == null)
            return null;
        return findByPaymentId(paymentId);
    }

    @Override
    public PaymentInfo findByOrderIdAndKey(int orderId, int idempotencyKey) {
        var attempts = orderIdToAttemptsInfo.get(orderId);
        if (attempts == null || !attempts.containsKey(idempotencyKey))
            return null;

        return findByPaymentId(attempts.get(idempotencyKey));
    }

    @Override
    public PaymentInfo findByPaymentId(int paymentId) {
        return paymentIdToPaymentInfoMap.get(paymentId);
    }

    @Override
    public int save(PaymentRequest paymentRequest, PaymentStatus paymentStatus) {
        PaymentInfo newPaymentInfo = createPayment(paymentRequest,
                                                   paymentStatus);
        orderIdToPaymentInfoMap.put(newPaymentInfo.getOrderId(), newPaymentInfo.getPaymentId());
        orderIdToAttemptsInfo.computeIfAbsent(newPaymentInfo.getOrderId(), k -> new HashMap<>())
                             .put(newPaymentInfo.getIdempotentKey(), newPaymentInfo.getPaymentId());
        paymentIdToPaymentInfoMap.put(newPaymentInfo.getPaymentId(), newPaymentInfo);

        return newPaymentInfo.getPaymentId();
    }

    private PaymentInfo createPayment(PaymentRequest paymentRequest, PaymentStatus paymentStatus) {
        return new PaymentInfo(paymentRequest.getOrderId(),
                               paymentIdGenerator.incrementAndGet(),
                               paymentRequest.getAmount(),
                               paymentRequest.getPaymentMethod(),
                               paymentRequest.getIdempotentKey(),
                               paymentStatus);
    }
}
