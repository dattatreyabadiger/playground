package coupon_management.service;

import coupon_management.model.Cart;
import coupon_management.model.CartItem;
import coupon_management.model.Coupon;
import coupon_management.repository.CouponRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CouponManagementService implements ICouponManagementService {

    private final CouponRepository couponRepository;

    public CouponManagementService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public List<Coupon> getApplicableCoupons(Cart cart) {
        List<Coupon> applicableCoupons = new ArrayList<>(couponRepository.getApplicable(cart));
        Set<Integer> cartItemIds = cart.getCartItemList()
                                       .stream()
                                       .map(CartItem::getId)
                                       .collect(Collectors.toSet());
        for (int cartItemId : cartItemIds) {
            Coupon itemSpecificCoupon = couponRepository.getItemSpecificCoupon(cartItemId);
            if (itemSpecificCoupon != null)
                applicableCoupons.add(itemSpecificCoupon);
        }
        return applicableCoupons;
    }

    @Override
    public BigDecimal getFinalPrice(Cart cart, int couponId) {
        Set<Integer> applicableCouponIds = getApplicableCoupons(cart).stream()
                                                                     .map(Coupon::getId)
                                                                     .collect(Collectors.toSet());
        if (!applicableCouponIds.contains(couponId))
            throw new IllegalArgumentException("Coupon cannot be applied to the cart");
        return couponRepository.getFinalPrice(cart, couponId);
    }
}
