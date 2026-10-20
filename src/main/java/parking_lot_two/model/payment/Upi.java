package parking_lot_two.model.payment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Upi implements PaymentMethod {

    @Override
    public void chargeAmount(int amount) {
        log.info("Payment of amount = {} via UPI", amount);
    }
}
