package coupon_management_two.discount;

import coupon_management_two.model.Cart;

import java.math.BigDecimal;

public class PercentageDiscountStrategy implements DiscountStrategy {

    private final BigDecimal percentageDiscount;

    public PercentageDiscountStrategy(BigDecimal percentageDiscount) {
        this.percentageDiscount = percentageDiscount;
    }

    @Override
    public BigDecimal getFinalPrice(Cart cart) {
        return cart.getTotalPrice()
                   .multiply(BigDecimal.valueOf(100)
                                       .subtract(percentageDiscount))
                   .divide(BigDecimal.valueOf(100));
    }
}
