package coupon_management.repository;

import coupon_management.model.Cart;
import coupon_management.model.Coupon;

import java.math.BigDecimal;
import java.util.List;

public interface CouponRepository {

    List<Coupon> getApplicable(Cart cart);

    BigDecimal getFinalPrice(Cart cart, int couponId);

    Coupon getItemSpecificCoupon(int cartItemId);
}
