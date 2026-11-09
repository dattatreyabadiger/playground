package cart_restriction.service;

import cart_restriction.model.Cart;
import cart_restriction.model.CartItem;
import cart_restriction.model.Category;
import cart_restriction.repository.CartRestrictionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CartValidatorService {

    private final CartRestrictionRepository cartRestrictionRepository;

    public CartValidatorService(CartRestrictionRepository cartRestrictionRepository) {
        this.cartRestrictionRepository = cartRestrictionRepository;
    }


    public List<String> validateCart(Cart cart) {
        Map<Category, Set<CartItem>> categoryToCartItemsMap = new HashMap<>();
        List<String> messages = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            if (!validateEachProductLimits(cartItem))
                messages.add("PRODUCT_ID = " + cartItem.getId() + " does not meet minimum limit");

            // to validate product category buying limits
            categoryToCartItemsMap.computeIfAbsent(cartItem.getCategory(), k -> new HashSet<>())
                                  .add(cartItem);
        }

        for (var categoryToCartItems : categoryToCartItemsMap.entrySet()) {
            Category category = categoryToCartItems.getKey();
            if (!validateProductCategoryLimits(category, categoryToCartItems.getValue()))
                messages.add("PRODUCT_CATEGORY = " + category + " exceeds max limit");
        }
        return messages;
    }

    private boolean validateEachProductLimits(CartItem cartItem) {
        return cartRestrictionRepository.validateProductQuantity(cartItem.getId(), cartItem.getQuantity());
    }

    private boolean validateProductCategoryLimits(Category category, Set<CartItem> cartItems) {
        int totalQuantity = 0;
        for (CartItem cartItem : cartItems) {
            totalQuantity += cartItem.getQuantity();
        }
        return cartRestrictionRepository.validateProductCategoryQuantity(category, totalQuantity);
    }
}
