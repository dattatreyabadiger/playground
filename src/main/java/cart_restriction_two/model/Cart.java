package cart_restriction_two.model;

import lombok.Getter;

import java.util.Map;

@Getter
public class Cart {

    private final int id;
    private final Map<Integer, CartItem> cartItems;

    public Cart(int id, Map<Integer, CartItem> cartItems) {
        this.id = id;
        this.cartItems = cartItems;
    }
}
