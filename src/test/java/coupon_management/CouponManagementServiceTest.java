package coupon_management;

import coupon_management.model.Cart;
import coupon_management.model.CartItem;
import coupon_management.model.Condition;
import coupon_management.model.Coupon;
import coupon_management.model.FlatRateDiscountCoupon;
import coupon_management.model.ItemSpecificDiscountCoupon;
import coupon_management.model.PercentageDiscountCoupon;
import coupon_management.repository.InMemoryCouponRepository;
import coupon_management.service.CouponManagementService;
import coupon_management.service.ICouponManagementService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CouponManagementServiceTest {

    private final ICouponManagementService couponManagementService;

    private final Condition condition1 = new Condition(new BigDecimal(100));
    private final Coupon coupon1 = new FlatRateDiscountCoupon(condition1, 20);

    private final Condition condition2 = new Condition(new BigDecimal(1000));
    private final Coupon coupon2 = new PercentageDiscountCoupon(condition2, 10);

    private final Coupon coupon3 = new ItemSpecificDiscountCoupon(condition1, 10, Set.of(1));

    public CouponManagementServiceTest() {
        Map<Integer, Coupon> coupons = Map.of(coupon1.getId(), coupon1,
                                              coupon2.getId(), coupon2,
                                              coupon3.getId(), coupon3);
        this.couponManagementService
                = new CouponManagementService(new InMemoryCouponRepository(coupons));
    }

    @Test
    public void getApplicableCouponsTest() {
        CartItem cartItem1 = new CartItem(new BigDecimal(10), 2);
        CartItem cartItem2 = new CartItem(new BigDecimal(20), 1);
        CartItem cartItem3 = new CartItem(new BigDecimal(30), 1);
        Cart cart = new Cart(List.of(cartItem1, cartItem2, cartItem3));

        assertEquals(Set.of(), couponManagementService.getApplicableCoupons(cart));

        CartItem cartItem4 = new CartItem(new BigDecimal(30), 5);
        Cart cart2 = new Cart(List.of(cartItem1, cartItem2, cartItem3, cartItem4));
        assertEquals(Set.of(coupon1, coupon3), couponManagementService.getApplicableCoupons(cart2));

        assertEquals(new BigDecimal(200), couponManagementService.getFinalPrice(cart2, coupon1.getId()));
        assertEquals(new BigDecimal(210), couponManagementService.getFinalPrice(cart2, coupon3.getId()));
        assertThrows(IllegalArgumentException.class,
                     () -> couponManagementService.getFinalPrice(cart2, coupon2.getId()));
    }
}
