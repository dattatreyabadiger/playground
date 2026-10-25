package coupon_management.model;


import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class FlatRateDiscountCoupon extends Coupon {

    private final int flatRateDiscount;

    public FlatRateDiscountCoupon(Condition condition, int flatRateDiscount) {
        super(condition);
        this.flatRateDiscount = flatRateDiscount;
    }

    @Override
    public BigDecimal getFinalPrice(Cart cart) {
        return cart.getTotalPrice()
                   .subtract(BigDecimal.valueOf(this.flatRateDiscount));
    }
}
