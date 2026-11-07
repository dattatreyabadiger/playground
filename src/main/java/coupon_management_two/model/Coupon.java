package coupon_management_two.model;

import coupon_management_two.coupon_eligibility.EligibilityStrategy;
import coupon_management_two.discount.DiscountStrategy;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Coupon {

    private static final AtomicInteger idGenerator = new AtomicInteger();

    private final int id;
    private final DiscountStrategy discountStrategy;
    private final EligibilityStrategy eligibilityStrategy;

    public Coupon(DiscountStrategy discountStrategy,
                  EligibilityStrategy eligibilityStrategy) {
        this.id = idGenerator.incrementAndGet();
        this.discountStrategy = discountStrategy;
        this.eligibilityStrategy = eligibilityStrategy;
    }

    public boolean isCouponApplicable(Cart cart) {
        return eligibilityStrategy.isCouponApplicable(cart);
    }

    public BigDecimal getFinalPrice(Cart cart) {
        return discountStrategy.getFinalPrice(cart);
    }
}
