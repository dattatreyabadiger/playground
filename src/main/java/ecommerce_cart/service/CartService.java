package ecommerce_cart.service;

import ecommerce_cart.model.CartStatusEnum;
import ecommerce_cart.model.ItemDto;
import ecommerce_cart.repository.CartRepository;
import lombok.AllArgsConstructor;

import java.math.BigInteger;

@AllArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;

    @Override
    public int createCart() {
        return cartRepository.createCart();
    }

    @Override
    public boolean addItemToCart(int cartId, ItemDto newItem) {
        if (cartRepository.getCart(cartId).getStatus() != CartStatusEnum.IN_PROGRESS)
            return false;

        cartRepository.addItemToCart(cartId, newItem);
        return true;
    }

    @Override
    public boolean removeItemFromCart(int cartId, ItemDto newItem) {
        if (cartRepository.getCart(cartId).getStatus() != CartStatusEnum.IN_PROGRESS)
            return false;

        cartRepository.removeItemFromCart(cartId, newItem);
        return true;
    }

    @Override
    public void checkoutCart(int cartId) {
        cartRepository.updateCartStatus(cartId, CartStatusEnum.CHECKED_OUT);
    }

    @Override
    public BigInteger getTotalPrice(int cartId) {
        return cartRepository.getCart(cartId).getTotalCartPrice();
    }
}
