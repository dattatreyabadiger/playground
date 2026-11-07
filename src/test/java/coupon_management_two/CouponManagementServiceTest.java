package coupon_management_two;

import coupon_management_two.coupon_eligibility.ItemSpecificEligibilityStrategy;
import coupon_management_two.coupon_eligibility.MinimumCartPriceEligibilityStrategy;
import coupon_management_two.discount.FlatDiscountStrategy;
import coupon_management_two.discount.PercentageDiscountStrategy;
import coupon_management_two.model.Cart;
import coupon_management_two.model.CartItem;
import coupon_management_two.model.Coupon;
import coupon_management_two.repository.InMemoryCouponRepository;
import coupon_management_two.service.CouponManagementService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CouponManagementServiceTest {

    private final CouponManagementService couponManagementService;

    FlatDiscountStrategy flatDiscount100
            = new FlatDiscountStrategy(BigDecimal.valueOf(100));

    PercentageDiscountStrategy percentageDiscount10
            = new PercentageDiscountStrategy(BigDecimal.valueOf(10));

    MinimumCartPriceEligibilityStrategy minCartPrice1000
            = new MinimumCartPriceEligibilityStrategy(BigDecimal.valueOf(1000));

    MinimumCartPriceEligibilityStrategy minCartPrice10000
            = new MinimumCartPriceEligibilityStrategy(BigDecimal.valueOf((10000)));

    ItemSpecificEligibilityStrategy itemSpecificEligibilityStrategy
            = new ItemSpecificEligibilityStrategy(Set.of(1));

    Coupon coupon1 = new Coupon(flatDiscount100, minCartPrice1000);
    Coupon coupon2 = new Coupon(percentageDiscount10, minCartPrice1000);
    Coupon coupon3 = new Coupon(percentageDiscount10, minCartPrice10000);
    Coupon coupon4 = new Coupon(percentageDiscount10, itemSpecificEligibilityStrategy);


    public CouponManagementServiceTest() {
        Map<Integer, Coupon> coupons = Map.of(coupon1.getId(), coupon1,
                                              coupon2.getId(), coupon2,
                                              coupon3.getId(), coupon3,
                                              coupon4.getId(), coupon4);
        InMemoryCouponRepository repository = new InMemoryCouponRepository(coupons);

        this.couponManagementService = new CouponManagementService(repository);
    }

    @Test
    public void getApplicableCouponsTest() {
        CartItem item1 = new CartItem("milk", BigDecimal.valueOf(100), 8);
        CartItem item2 = new CartItem("protein", BigDecimal.valueOf(2000), 1);

        Cart cart = new Cart(List.of(item1, item2));
        Set<Coupon> actualCoupons = couponManagementService.getApplicableCoupons(cart);
        assertEquals(Set.of(coupon1, coupon2, coupon4), actualCoupons);

        Cart cart2 = new Cart(List.of(item2));
        Set<Coupon> actualCoupons2 = couponManagementService.getApplicableCoupons(cart2);
        assertEquals(Set.of(coupon1, coupon2), actualCoupons2);
    }

    @Test
    public void getFinalPrice() {
        CartItem item1 = new CartItem("milk", BigDecimal.valueOf(100), 8);
        CartItem item2 = new CartItem("protein", BigDecimal.valueOf(2000), 1);

        Cart cart = new Cart(List.of(item1, item2));
        BigDecimal actualFinalPrice = couponManagementService.getDiscountedPrice(cart, coupon2.getId());
        assertEquals(BigDecimal.valueOf(2520), actualFinalPrice);

        BigDecimal actualFinalPrice2 = couponManagementService.getDiscountedPrice(cart, coupon4.getId());
        assertEquals(BigDecimal.valueOf(2520), actualFinalPrice2);

        assertThrows(IllegalStateException.class,
                     () -> couponManagementService.getDiscountedPrice(cart, coupon3.getId()));

        Cart cart2 = new Cart(List.of(item2));
        assertThrows(IllegalStateException.class,
                     () -> couponManagementService.getDiscountedPrice(cart2, coupon4.getId()));
    }
}
