package payment_gateway.repository;

import payment_gateway.model.PaymentInfo;
import payment_gateway.model.PaymentRequest;
import payment_gateway.model.PaymentStatus;

public interface PaymentRepository {

    PaymentInfo findByOrderId(int orderId);

    PaymentInfo findByOrderIdAndKey(int orderId, int idempotencyKey);

    PaymentInfo findByPaymentId(int paymentId);

    int save(PaymentRequest paymentRequest, PaymentStatus paymentStatus);
}
