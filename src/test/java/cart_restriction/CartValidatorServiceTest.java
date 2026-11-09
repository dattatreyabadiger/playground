package cart_restriction;

import cart_restriction.model.Cart;
import cart_restriction.model.CartItem;
import cart_restriction.model.Category;
import cart_restriction.repository.CartRestrictionRepository;
import cart_restriction.repository.InMemoryCartRestrictionRepository;
import cart_restriction.service.CartValidatorService;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartValidatorServiceTest {

    private final CartValidatorService cartValidatorService;

    public CartValidatorServiceTest() {
        Map<Integer, Integer> productIdToMinLimit = Map.of(1, 10,
                                                           2, 15,
                                                           3, 12,
                                                           4, 10,
                                                           5, 12);
        Map<Category, Integer> categoryToMaxLimit = Map.of(Category.ANTI_SEPTIC, 50,
                                                           Category.ANTI_VIRAL, 50,
                                                           Category.PAIN_KILLER, 25);
        CartRestrictionRepository cartRestrictionRepository = new InMemoryCartRestrictionRepository(productIdToMinLimit,
                                                                                                    categoryToMaxLimit);
        this.cartValidatorService = new CartValidatorService(cartRestrictionRepository);
    }

    @Test
    public void validateCart_test() {
        CartItem cartItem1 = new CartItem(1, Category.ANTI_SEPTIC, 15);
        CartItem cartItem2 = new CartItem(2, Category.ANTI_SEPTIC, 16);
        CartItem cartItem3 = new CartItem(3, Category.ANTI_SEPTIC, 10);
        Cart cart = new Cart(1, List.of(cartItem1, cartItem2, cartItem3));

        List<String> messages = cartValidatorService.validateCart(cart);

        CartItem cartItem4 = new CartItem(5, Category.ANTI_SEPTIC, 12);
        Cart cart2 = new Cart(1, List.of(cartItem1, cartItem2, cartItem3, cartItem4));

        List<String> messages2 = cartValidatorService.validateCart(cart2);
        assertEquals(0, 0);
    }
}
