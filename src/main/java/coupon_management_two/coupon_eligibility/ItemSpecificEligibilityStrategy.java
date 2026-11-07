package coupon_management_two.coupon_eligibility;

import coupon_management_two.model.Cart;
import coupon_management_two.model.CartItem;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemSpecificEligibilityStrategy implements EligibilityStrategy {

    private final Set<Integer> applicableItems;

    public ItemSpecificEligibilityStrategy(Set<Integer> applicableItems) {
        this.applicableItems = applicableItems;
    }

    @Override
    public boolean isCouponApplicable(Cart cart) {
        Set<Integer> cartItemIds = cart.getCartItems()
                                       .stream()
                                       .map(CartItem::getId)
                                       .collect(Collectors.toSet());
        return !Collections.disjoint(applicableItems, cartItemIds);
    }
}
