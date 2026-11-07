package coupon_management_two.repository;

import coupon_management_two.model.Cart;
import coupon_management_two.model.Coupon;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class InMemoryCouponRepository implements CouponRepository {

    private final Map<Integer, Coupon> coupons;

    public InMemoryCouponRepository(Map<Integer, Coupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public Set<Coupon> getApplicableCoupon(Cart cart) {
        return coupons.values()
                      .stream()
                      .filter(coupon -> coupon.isCouponApplicable(cart))
                      .collect(Collectors.toSet());
    }

    @Override
    public BigDecimal getFinalPrice(Cart cart, int couponId) {
        Coupon coupon = coupons.get(couponId);

        if (!coupon.isCouponApplicable(cart))
            throw new IllegalStateException("Coupon not applicable for the Cart");

        return coupon.getFinalPrice(cart);
    }
}
