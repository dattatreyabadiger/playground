package cart_restriction_two;

import cart_restriction_two.cart_restriction.CartRestrictionRule;
import cart_restriction_two.model.Cart;
import cart_restriction_two.model.CartItem;
import cart_restriction_two.model.Category;
import cart_restriction_two.model.ResultSet;
import cart_restriction_two.repository.CartRestrictionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CartValidatorService {

    private final CartRestrictionRepository cartRestrictionRepository;

    public CartValidatorService(CartRestrictionRepository cartRestrictionRepository) {
        this.cartRestrictionRepository = cartRestrictionRepository;
    }

    public List<ResultSet> validateCart(Cart cart) {
        List<CartRestrictionRule> applicableCartRestrictionRules = new ArrayList<>();
        applicableCartRestrictionRules.addAll(getCartRestrictionRulesForProduct(cart));
        applicableCartRestrictionRules.addAll(getCartRestrictionRulesForCategories(cart));

        List<ResultSet> results = new ArrayList<>();
        for (CartRestrictionRule rule : applicableCartRestrictionRules) {
            ResultSet resultSet = rule.validateCart(cart);
            if (resultSet != null)
                results.add(resultSet);
        }
        return results;
    }

    private List<CartRestrictionRule> getCartRestrictionRulesForProduct(Cart cart) {
        Set<Integer> productIds = cart.getCartItems()
                                      .keySet();
        return cartRestrictionRepository.getCartRestrictionRulesForProducts(productIds);
    }

    private List<CartRestrictionRule> getCartRestrictionRulesForCategories(Cart cart) {
        Set<Category> productCategories = cart.getCartItems()
                                              .values()
                                              .stream()
                                              .map(CartItem::getCategory)
                                              .collect(Collectors.toSet());
        return cartRestrictionRepository.getCartRestrictionRulesForCategories(productCategories);
    }
}
