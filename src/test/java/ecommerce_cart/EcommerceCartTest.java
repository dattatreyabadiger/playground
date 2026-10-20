package ecommerce_cart;

import ecommerce_cart.model.CartItemDto;
import ecommerce_cart.repository.InMemoryCartRepository;
import ecommerce_cart.repository.InMemoryInventoryRepository;
import ecommerce_cart.service.CartService;
import ecommerce_cart.service.ICartService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EcommerceCartTest {

    private final ICartService cartService;

    public EcommerceCartTest() {
        this.cartService = new CartService(new InMemoryCartRepository(),
                                           new InMemoryInventoryRepository());
    }

    @Test
    public void createCartTest() {
        int cartId = cartService.createCart();
        assertNotEquals(0, cartId);
    }

    @Test
    public void addItemToCartTest() {
        int cartId = cartService.createCart();

        boolean isItemAdded = cartService.addItem(cartId, 1);
        assertTrue(isItemAdded);

        // simulate cart addition for unavailable item
        boolean isItemAdded2 = cartService.addItem(cartId, 5);
        assertFalse(isItemAdded2);

        // simulate cart addition for unavailable item quantity
        boolean isItemAdded3 = cartService.updateItemQuantity(cartId, 1, 10000);
        assertFalse(isItemAdded3);

        //simulate cart checkout
        assertTrue(cartService.checkoutCart(cartId));
        boolean isItemAdded4 = cartService.addItem(cartId, 1);
        assertFalse(isItemAdded4);
    }

    @Test
    public void getTotalPriceTest() {
        int cartId = cartService.createCart();
        cartService.addItem(cartId, 1);
        cartService.addItem(cartId, 2);

        // view all Items in cart
        CartItemDto expectedCartItem1 = new CartItemDto(1, "item1", 1, BigDecimal.valueOf(100));
        CartItemDto expectedCartItem2 = new CartItemDto(2, "item2", 1, BigDecimal.valueOf(150));
        List<CartItemDto> actualCartItems = cartService.getCartItems(cartId);
        assertEquals(Set.of(expectedCartItem1, expectedCartItem2),
                     new HashSet<>(actualCartItems));

        BigDecimal totalPrice = cartService.getTotalPrice(cartId);
        assertEquals(BigDecimal.valueOf(250), totalPrice);
    }

    @Test
    public void emptyCartTotalPriceTest() {
        int cartId = cartService.createCart();
        assertEquals(BigDecimal.ZERO, cartService.getTotalPrice(cartId));
    }
}
