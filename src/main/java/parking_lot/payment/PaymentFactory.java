package parking_lot.payment;

import parking_lot.model.PaymentType;

import java.util.HashMap;
import java.util.Map;

// FACTORY PATTERN: builds and encapsulates all Payment strategies keyed by PaymentType.
// The map is private - callers never receive raw Payment instances, only go through
// chargeAmount(), keeping strategy creation and selection fully encapsulated.
public class PaymentFactory {

    private final Map<PaymentType, Payment> paymentTypes = new HashMap<>();

    public PaymentFactory() {
        paymentTypes.put(PaymentType.CARD, new CardPayment());
        paymentTypes.put(PaymentType.CASH, new CashPayment());
        paymentTypes.put(PaymentType.UPI, new UpiPayment());
    }

    public void chargeAmount(PaymentType paymentType, int amount) {
        paymentTypes.get(paymentType)
                    .chargeAmount(amount);
    }
}
