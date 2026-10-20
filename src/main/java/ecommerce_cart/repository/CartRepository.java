package ecommerce_cart.repository;

import ecommerce_cart.model.CartDto;
import ecommerce_cart.model.CartStatusEnum;
import ecommerce_cart.model.ItemDto;

import java.util.List;

public interface CartRepository {

    CartDto getCart(int id);

    int createCart();

    void updateCartStatus(int cartId, CartStatusEnum newCartStatus);

    List<ItemDto> getItems(int cartId);

    void addItemToCart(int cartId, ItemDto newItem);

    void removeItemFromCart(int cartId, ItemDto removeItem);
}
