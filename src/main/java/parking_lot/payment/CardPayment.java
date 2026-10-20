package parking_lot.payment;


// STRATEGY PATTERN (concrete strategy).
public class CardPayment implements Payment {

    public CardPayment() {}

    public void chargeAmount(int amount) {
        System.out.println("Card Payment done for amount: " + amount);
    }
}
