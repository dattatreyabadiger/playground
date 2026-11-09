package cart_restriction_two.cart_restriction;

import cart_restriction_two.model.Cart;
import cart_restriction_two.model.ResultSet;

public interface CartRestrictionRule {

    ResultSet validateCart(Cart cart);
}
