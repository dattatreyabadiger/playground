package parking_lot.payment;


// STRATEGY PATTERN (concrete strategy).
public class CashPayment implements Payment {

    public CashPayment() {}

    public void chargeAmount(int amount) {
        System.out.println("Cash Payment done for amount: " + amount);
    }
}
