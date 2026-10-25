package coupon_management.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Coupon {

    private int id;
    private Condition condition;
    private static final AtomicInteger idGenerator = new AtomicInteger();

    public Coupon(Condition condition) {
        this.id = idGenerator.incrementAndGet();
        this.condition = condition;
    }

    public BigDecimal getFinalPrice(Cart cart) {
        return new BigDecimal(0);
    }

    public boolean isConditionApplicable(Cart cart) {
        return cart.getTotalPrice()
                   .compareTo(this.condition.getMinCartValue()) > 0;
    }

}
