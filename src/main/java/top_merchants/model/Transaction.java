package top_merchants.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {

    private final int transactionId;
    private final int merchantId;

    public Transaction(int transactionId, int merchantId) {
        this.transactionId = transactionId;
        this.merchantId = merchantId;
    }
}
