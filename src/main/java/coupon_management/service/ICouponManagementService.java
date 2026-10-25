package coupon_management.service;

import coupon_management.model.Cart;
import coupon_management.model.Coupon;

import java.math.BigDecimal;
import java.util.List;

public interface ICouponManagementService {

    List<Coupon> getApplicableCoupons(Cart cart);

    BigDecimal getFinalPrice(Cart cart, int couponId);
}
