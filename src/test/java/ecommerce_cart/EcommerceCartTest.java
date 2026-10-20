package ecommerce_cart;

import ecommerce_cart.model.ItemDto;
import ecommerce_cart.repository.InMemoryCartRepository;
import ecommerce_cart.service.CartService;
import ecommerce_cart.service.ICartService;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EcommerceCartTest {

    private final ICartService cartService;

    public EcommerceCartTest() {
        this.cartService = new CartService(new InMemoryCartRepository());
    }

    @Test
    public void createCartTest() {
        int cartId = cartService.createCart();
        assertNotEquals(0, cartId);
    }

    @Test
    public void addItemToCartTest() {
        int cartId = cartService.createCart();
        ItemDto item1 = new ItemDto(1, "item1", 1, new BigInteger(String.valueOf(100)));

        boolean isItemAdded = cartService.addItemToCart(cartId, item1);
        assertTrue(isItemAdded);

        //simulate cart checkout
        cartService.checkoutCart(cartId);
        boolean isItemAdded2 = cartService.addItemToCart(cartId, item1);
        assertFalse(isItemAdded2);
    }

    @Test
    public void getTotalPriceTest() {
        int cartId = cartService.createCart();
        ItemDto item1 = new ItemDto(1, "item1", 2, new BigInteger(String.valueOf(100)));
        ItemDto item2 = new ItemDto(2, "item2", 3, new BigInteger(String.valueOf(50)));
        cartService.addItemToCart(cartId, item1);
        cartService.addItemToCart(cartId, item2);

        BigInteger totalPrice = cartService.getTotalPrice(cartId);
        assertEquals(new BigInteger(String.valueOf(350)), totalPrice);
    }
}
