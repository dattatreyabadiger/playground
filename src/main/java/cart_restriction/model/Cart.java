package cart_restriction.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Cart {

    private final int id;
    private final List<CartItem> cartItems;

    public Cart(int id, List<CartItem> cartItems) {
        this.id = id;
        this.cartItems = cartItems;
    }
}
