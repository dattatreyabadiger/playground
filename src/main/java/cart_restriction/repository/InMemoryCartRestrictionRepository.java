package cart_restriction.repository;

import cart_restriction.model.Category;

import java.util.Map;

public class InMemoryCartRestrictionRepository implements CartRestrictionRepository {

    private final Map<Integer, Integer> productIdToMinBuyingLimitMap;
    private final Map<Category, Integer> categoryToMaxBuyingLimitMap;

    public InMemoryCartRestrictionRepository(Map<Integer, Integer> productIdToMinBuyingLimitMap,
                                             Map<Category, Integer> categoryToMaxBuyingLimitMap) {
        this.productIdToMinBuyingLimitMap = productIdToMinBuyingLimitMap;
        this.categoryToMaxBuyingLimitMap = categoryToMaxBuyingLimitMap;
    }

    @Override
    public boolean validateProductQuantity(int productId, int quantity) {
        Integer minBuyingLimit = productIdToMinBuyingLimitMap.get(productId);
        return quantity >= minBuyingLimit;
    }

    @Override
    public boolean validateProductCategoryQuantity(Category category, int quantity) {
        Integer maxBuyingLimit = categoryToMaxBuyingLimitMap.get(category);
        return quantity <= maxBuyingLimit;
    }
}
