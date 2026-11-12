package payment_gateway.model;

import lombok.Getter;

@Getter
public class PaymentInfo {

    private final int orderId;
    private final int paymentId;
    private final int amount;
    private final PaymentMethod paymentMethod;
    private final int idempotentKey;
    private final PaymentStatus paymentStatus;

    public PaymentInfo(int orderId, int paymentId,
                       int amount, PaymentMethod paymentMethod,
                       int idempotentKey, PaymentStatus paymentStatus) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.idempotentKey = idempotentKey;
        this.paymentStatus = paymentStatus;
    }
}
