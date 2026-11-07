package coupon_management_two.service;

import coupon_management_two.model.Cart;
import coupon_management_two.model.Coupon;
import coupon_management_two.repository.CouponRepository;

import java.math.BigDecimal;
import java.util.Set;

public class CouponManagementService implements ICouponManagementService {

    private final CouponRepository couponRepository;

    public CouponManagementService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Set<Coupon> getApplicableCoupons(Cart cart) {
        return couponRepository.getApplicableCoupon(cart);
    }

    public BigDecimal getDiscountedPrice(Cart cart, int couponId) {
        return couponRepository.getFinalPrice(cart, couponId);
    }
}
