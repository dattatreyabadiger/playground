package cart_restriction_two.cart_restriction;

import cart_restriction_two.model.Cart;
import cart_restriction_two.model.CartItem;
import cart_restriction_two.model.Category;
import cart_restriction_two.model.ResultSet;

public class MaximumProductCategoryQuantityRule implements CartRestrictionRule {

    private final Category category;
    private final int maxQuantity;

    public MaximumProductCategoryQuantityRule(Category category, int maxQuantity) {
        this.category = category;
        this.maxQuantity = maxQuantity;
    }

    public ResultSet validateCart(Cart cart) {
        int totalQuantityForCategory = 0;
        for (CartItem cartItem : cart.getCartItems()
                                     .values()) {
            if (cartItem.getCategory() != category)
                continue;
            totalQuantityForCategory += cartItem.getQuantity();
        }

        if (totalQuantityForCategory == 0)
            // rule not applicable for Cart
            return null;

        if (totalQuantityForCategory <= maxQuantity)
            // cart is valid as per rule
            return null;

        return new ResultSet("Cart has maximum quantity for PRODUCT_CATEGORY = " + category);
    }
}
