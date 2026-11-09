package cart_restriction_two.repository;

import cart_restriction_two.cart_restriction.CartRestrictionRule;
import cart_restriction_two.model.Category;

import java.util.List;
import java.util.Set;

public interface CartRestrictionRepository {

    List<CartRestrictionRule> getCartRestrictionRulesForProducts(Set<Integer> productIds);

    List<CartRestrictionRule> getCartRestrictionRulesForCategories(Set<Category> categories);
}
