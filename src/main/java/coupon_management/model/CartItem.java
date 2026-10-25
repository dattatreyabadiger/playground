package coupon_management.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class CartItem {

    private int id;
    private BigDecimal unitPrice;
    private int quantity;

    private static final AtomicInteger idGenerator = new AtomicInteger();

    public CartItem(BigDecimal unitPrice, int quantity) {
        this.id = idGenerator.incrementAndGet();
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }
}
