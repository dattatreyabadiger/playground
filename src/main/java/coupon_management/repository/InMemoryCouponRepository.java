package coupon_management.repository;

import coupon_management.model.Cart;
import coupon_management.model.Coupon;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class InMemoryCouponRepository implements CouponRepository {

    private final Map<Integer, Coupon> couponIdToCouponsMap;

    public InMemoryCouponRepository(Map<Integer, Coupon> coupons) {
        this.couponIdToCouponsMap = coupons;
    }

    @Override
    public Set<Coupon> getApplicable(Cart cart) {
        return couponIdToCouponsMap.values()
                                   .stream()
                                   .filter(coupon -> coupon.isConditionApplicable(cart))
                                   .collect(Collectors.toSet());
    }

    @Override
    public BigDecimal getFinalPrice(Cart cart, int couponId) {
        return couponIdToCouponsMap.get(couponId)
                                   .getFinalPrice(cart);
    }

}
