package parking_lot.payment;


// STRATEGY PATTERN (concrete strategy).
public class UpiPayment implements Payment {

    public UpiPayment() {}

    public void chargeAmount(int amount) {
        System.out.println("Upi Payment done for amount: " + amount);
    }
}
