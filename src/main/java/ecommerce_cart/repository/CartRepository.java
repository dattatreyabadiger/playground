package ecommerce_cart.repository;

import ecommerce_cart.model.CartDto;
import ecommerce_cart.model.CartItemDto;
import ecommerce_cart.model.CartStatusEnum;

import java.util.List;

public interface CartRepository {

    CartDto getCart(int id);

    int createCart();

    void updateCartStatus(int cartId, CartStatusEnum newCartStatus);

    List<CartItemDto> getItems(int cartId);

    void addItemToCart(int cartId, CartItemDto newItem);

    void removeItemFromCart(int cartId, int itemId);

    void updateItemQuantity(int cartId, CartItemDto item, int quantity);
}
