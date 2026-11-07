package coupon_management_two.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class CartItem {

    private static final AtomicInteger idGenerator = new AtomicInteger();

    private final int id;
    private final String name;
    private final BigDecimal unitPrice;
    private final int quantity;

    public CartItem(String name, BigDecimal unitPrice, int quantity) {
        this.id = idGenerator.incrementAndGet();
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }
}
