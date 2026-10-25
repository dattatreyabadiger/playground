package coupon_management.repository;

import coupon_management.model.Cart;
import coupon_management.model.Coupon;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class InMemoryCouponRepository implements CouponRepository {

    private final Map<Integer, Coupon> couponIdToCouponsMap;
    private final Map<Integer, Integer> itemSpecicificCouponIdsMap;

    public InMemoryCouponRepository(Map<Integer, Coupon> coupons, Map<Integer, Integer> itemSpecicificCouponIdsMap) {
        this.couponIdToCouponsMap = coupons;
        this.itemSpecicificCouponIdsMap = itemSpecicificCouponIdsMap;
    }

    @Override
    public List<Coupon> getApplicable(Cart cart) {
        return couponIdToCouponsMap.values()
                                   .stream()
                                   .filter(coupon -> cart.getTotalPrice()
                                                         .compareTo(coupon.getCondition()
                                                                          .getMinCartValue()) > 0)
                                   .toList();
    }

    @Override
    public BigDecimal getFinalPrice(Cart cart, int couponId) {
        return couponIdToCouponsMap.get(couponId)
                                   .getFinalPrice(cart);
    }

    @Override
    public Coupon getItemSpecificCoupon(int cartItemId) {
        Integer couponId = itemSpecicificCouponIdsMap.get(cartItemId);
        if (couponId == null)
            return null;
        return couponIdToCouponsMap.get(couponId);
    }

}
