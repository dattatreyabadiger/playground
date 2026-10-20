package ecommerce_cart.service;

import ecommerce_cart.model.ItemDto;

import java.math.BigInteger;

public interface ICartService {

    int createCart();

    boolean addItemToCart(int cartId, ItemDto newItem);

    boolean removeItemFromCart(int cartId, ItemDto newItem);

    void checkoutCart(int cartId);

    BigInteger getTotalPrice(int cartId);
}
