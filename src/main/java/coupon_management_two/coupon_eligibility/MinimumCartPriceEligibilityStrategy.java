package coupon_management_two.coupon_eligibility;

import coupon_management_two.model.Cart;

import java.math.BigDecimal;

public class MinimumCartPriceEligibilityStrategy implements EligibilityStrategy {

    private final BigDecimal minimumCartPrice;

    public MinimumCartPriceEligibilityStrategy(BigDecimal minimumCartPrice) {
        this.minimumCartPrice = minimumCartPrice;
    }

    @Override
    public boolean isCouponApplicable(Cart cart) {
        return cart.getTotalPrice()
                   .compareTo(minimumCartPrice) > 0;
    }
}
