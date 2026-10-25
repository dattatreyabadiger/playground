package coupon_management.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemSpecificDiscountCoupon extends FlatRateDiscountCoupon {

    private final Set<Integer> itemsApplicable;

    public ItemSpecificDiscountCoupon(Condition condition, int flatRateDiscount, Set<Integer> itemsApplicable) {
        super(condition, flatRateDiscount);
        this.itemsApplicable = itemsApplicable;
    }

    @Override
    public BigDecimal getFinalPrice(Cart cart) {
        return cart.getTotalPrice()
                   .subtract(BigDecimal.valueOf(this.getFlatRateDiscount()));
    }

    @Override
    public boolean isConditionApplicable(Cart cart) {
        Set<Integer> cartItemIds = cart.getCartItemList()
                                       .stream()
                                       .map(CartItem::getId)
                                       .collect(Collectors.toSet());
        return cart.getTotalPrice()
                   .compareTo(this.getCondition()
                                  .getMinCartValue()) > 0
                && !Collections.disjoint(cartItemIds, itemsApplicable);
    }
}
