package cart_restriction_two;

import cart_restriction_two.cart_restriction.MaximumProductCategoryQuantityRule;
import cart_restriction_two.cart_restriction.MinimumProductQuantityRule;
import cart_restriction_two.cart_restriction.CartRestrictionRule;
import cart_restriction_two.model.Cart;
import cart_restriction_two.model.CartItem;
import cart_restriction_two.model.Category;
import cart_restriction_two.model.ResultSet;
import cart_restriction_two.repository.CartRestrictionRepository;
import cart_restriction_two.repository.InMemoryCartRestrictionRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartValidatorServiceTest {

    private final CartValidatorService cartValidatorService;

    public CartValidatorServiceTest() {
        Map<Integer, CartRestrictionRule> productRules
                = Map.of(1, new MinimumProductQuantityRule(1, 10),
                         2, new MinimumProductQuantityRule(2, 15),
                         3, new MinimumProductQuantityRule(3, 12),
                         4, new MinimumProductQuantityRule(4, 10),
                         5, new MinimumProductQuantityRule(5, 12));
        Map<Category, CartRestrictionRule> categoryRules
                = Map.of(Category.ANTISEPTIC, new MaximumProductCategoryQuantityRule(Category.ANTISEPTIC, 50),
                         Category.ANTIVIRAL, new MaximumProductCategoryQuantityRule(Category.ANTIVIRAL, 50),
                         Category.PAINKILLER, new MaximumProductCategoryQuantityRule(Category.PAINKILLER, 50));
        CartRestrictionRepository cartRestrictionRepository = new InMemoryCartRestrictionRepository(productRules,
                                                                                                    categoryRules);
        this.cartValidatorService = new CartValidatorService(cartRestrictionRepository);
    }

    @Test
    public void validateCart_test() {
        CartItem cartItem1 = new CartItem(1, Category.ANTISEPTIC, 15);
        CartItem cartItem2 = new CartItem(2, Category.ANTISEPTIC, 16);
        CartItem cartItem3 = new CartItem(3, Category.ANTISEPTIC, 10);
        Cart cart = new Cart(1, Map.of(1, cartItem1, 2, cartItem2, 3, cartItem3));

        List<ResultSet> results = cartValidatorService.validateCart(cart);
        assertEquals(1, results.size());
        assertEquals("Cart has no minimum quantity for PRODUCT_ID = 3", results.get(0)
                                                                               .getMessage());

        CartItem cartItem4 = new CartItem(5, Category.ANTISEPTIC, 12);
        Cart cart2 = new Cart(1, Map.of(1, cartItem1, 2, cartItem2, 3, cartItem3, 4, cartItem4));
        List<ResultSet> results2 = cartValidatorService.validateCart(cart2);
        assertEquals(2, results2.size());
        assertEquals("Cart has no minimum quantity for PRODUCT_ID = 3", results2.get(0)
                                                                                .getMessage());
        assertEquals("Cart has maximum quantity for PRODUCT_CATEGORY = ANTISEPTIC", results2.get(1)
                                                                                            .getMessage());
    }
}
