package payment_gateway.model;

import lombok.Getter;

@Getter
public class PaymentRequest {

    private final int orderId;
    private final int amount;
    private final PaymentMethod paymentMethod;
    private final int idempotentKey;

    public PaymentRequest(int orderId, int amount, PaymentMethod paymentMethod, int idempotentKey) {
        this.orderId = orderId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.idempotentKey = idempotentKey;
    }
}
