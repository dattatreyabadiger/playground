package parking_lot_two.model.payment;

import java.util.HashMap;
import java.util.Map;

public class PaymentFactory {

    private final Map<PaymentType, PaymentMethod> paymentMap = new HashMap<>();

    public PaymentFactory() {
        this.paymentMap.put(PaymentType.CARD, new Card());
        this.paymentMap.put(PaymentType.CASH, new Cash());
        this.paymentMap.put(PaymentType.UPI, new Upi());
    }

    public void chargeAmount(PaymentType paymentType, Integer amount) {
        paymentMap.get(paymentType)
                  .chargeAmount(amount);
    }
}
