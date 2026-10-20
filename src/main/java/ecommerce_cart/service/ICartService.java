package ecommerce_cart.service;

import ecommerce_cart.model.CartItemDto;

import java.math.BigDecimal;
import java.util.List;

public interface ICartService {

    int createCart();

    List<CartItemDto> getCartItems(int cartId);

    boolean addItem(int cartId, int itemId);

    boolean removeItem(int cartId, int itemId);

    boolean updateItemQuantity(int cartId, int itemId, int quantity);

    boolean checkoutCart(int cartId);

    BigDecimal getTotalPrice(int cartId);
}
