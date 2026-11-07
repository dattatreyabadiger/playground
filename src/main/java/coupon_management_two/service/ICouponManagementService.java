package coupon_management_two.service;

import coupon_management_two.model.Cart;
import coupon_management_two.model.Coupon;

import java.math.BigDecimal;
import java.util.Set;

public interface ICouponManagementService {

    Set<Coupon> getApplicableCoupons(Cart cart);

    BigDecimal getDiscountedPrice(Cart cart, int couponId);
}
