package parking_lot.payment;

// STRATEGY PATTERN (interface): declares one interchangeable payment algorithm.
// CardPayment/CashPayment/UpiPayment are concrete strategies plugged in via this contract.
public interface Payment {

    void chargeAmount(int amount);
}
