package coupon_management_two.coupon_eligibility;

import coupon_management_two.model.Cart;

public interface EligibilityStrategy {

    boolean isCouponApplicable(Cart cart);
}
