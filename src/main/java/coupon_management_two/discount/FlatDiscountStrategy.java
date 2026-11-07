package coupon_management_two.discount;

import coupon_management_two.model.Cart;

import java.math.BigDecimal;

public class FlatDiscountStrategy implements DiscountStrategy {

    private final BigDecimal flatDiscount;

    public FlatDiscountStrategy(BigDecimal flatDiscount) {
        this.flatDiscount = flatDiscount;
    }

    @Override
    public BigDecimal getFinalPrice(Cart cart) {
        return cart.getTotalPrice()
                   .subtract(flatDiscount);
    }
}
