package coupon_management.repository;

import coupon_management.model.Cart;
import coupon_management.model.Coupon;

import java.math.BigDecimal;
import java.util.Set;

public interface CouponRepository {

    Set<Coupon> getApplicable(Cart cart);

    BigDecimal getFinalPrice(Cart cart, int couponId);
}
