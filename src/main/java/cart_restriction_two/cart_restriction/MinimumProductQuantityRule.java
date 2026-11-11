package cart_restriction_two.cart_restriction;

import cart_restriction_two.model.Cart;
import cart_restriction_two.model.CartItem;
import cart_restriction_two.model.ResultSet;

public class MinimumProductQuantityRule implements CartRestrictionRule {

    private final int productId;
    private final int minQuantity;

    public MinimumProductQuantityRule(int productId, int minQuantity) {
        this.productId = productId;
        this.minQuantity = minQuantity;
    }

    public ResultSet validateCart(Cart cart) {
        CartItem cartItem = cart.getCartItems()
                                .get(productId);

        if (cartItem == null)
            // rule not applicable for Cart
            return null;

        if (cartItem.getQuantity() >= minQuantity)
            // cart is valid as per rule
            return null;

        return new ResultSet("Cart has no minimum quantity for PRODUCT_ID = " + productId);
    }
}
