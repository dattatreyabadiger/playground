package coupon_management_two.repository;

import coupon_management_two.model.Cart;
import coupon_management_two.model.Coupon;

import java.math.BigDecimal;
import java.util.Set;

public interface CouponRepository {

    Set<Coupon> getApplicableCoupon(Cart cart);

    BigDecimal getFinalPrice(Cart cart, int couponId);
}
