package cart_restriction_two.repository;

import cart_restriction_two.cart_restriction.CartRestrictionRule;
import cart_restriction_two.model.Category;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class InMemoryCartRestrictionRepository implements CartRestrictionRepository {

    private final Map<Integer, CartRestrictionRule> restrictionRulesByProductId;
    private final Map<Category, CartRestrictionRule> restrictionRulesByProductCategory;

    public InMemoryCartRestrictionRepository(Map<Integer, CartRestrictionRule> restrictionRulesByProductId,
                                             Map<Category, CartRestrictionRule> restrictionRulesByProductCategory) {
        this.restrictionRulesByProductId = restrictionRulesByProductId;
        this.restrictionRulesByProductCategory = restrictionRulesByProductCategory;
    }

    @Override
    public List<CartRestrictionRule> getCartRestrictionRulesForProducts(Set<Integer> productIds) {
        return productIds.stream()
                         .map(restrictionRulesByProductId::get)
                         .filter(Objects::nonNull)
                         .toList();
    }

    @Override
    public List<CartRestrictionRule> getCartRestrictionRulesForCategories(Set<Category> category) {
        return category.stream()
                         .map(restrictionRulesByProductCategory::get)
                         .filter(Objects::nonNull)
                         .toList();
    }
}
