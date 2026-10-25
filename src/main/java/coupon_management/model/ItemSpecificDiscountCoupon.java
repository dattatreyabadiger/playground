package coupon_management.model;

import java.math.BigDecimal;
import java.util.Set;

public class ItemSpecificDiscountCoupon extends FlatRateDiscountCoupon {

    private final Set<Integer> itemsApplicable;

    public ItemSpecificDiscountCoupon(Condition condition, int flatRateDiscount, Set<Integer> itemsApplicable) {
        super(condition, flatRateDiscount);
        this.itemsApplicable = itemsApplicable;
    }

    @Override
    public BigDecimal getFinalPrice(Cart cart) {
        boolean isCouponApplicable = cart.getCartItemList()
                                         .stream()
                                         .anyMatch(cartItem -> itemsApplicable.contains(cartItem.getId()));

        if (!isCouponApplicable)
            return cart.getTotalPrice();
        return cart.getTotalPrice()
                   .subtract(BigDecimal.valueOf(this.getFlatRateDiscount()));
    }
}
