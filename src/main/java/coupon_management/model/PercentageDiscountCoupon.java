package coupon_management.model;


import java.math.BigDecimal;

public class PercentageDiscountCoupon extends Coupon {

    private final int percentageDiscount;

    public PercentageDiscountCoupon(Condition condition, int percentageDiscount) {
        super(condition);
        this.percentageDiscount = percentageDiscount;
    }

    @Override
    public BigDecimal getFinalPrice(Cart cart) {
        return cart.getTotalPrice()
                   .multiply(BigDecimal.valueOf(100 - this.percentageDiscount))
                   .divide(BigDecimal.valueOf(100));
    }
}
