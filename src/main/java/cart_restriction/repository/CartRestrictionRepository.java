package cart_restriction.repository;

import cart_restriction.model.Category;

public interface CartRestrictionRepository {


    boolean validateProductQuantity(int productId, int quantity);

    boolean validateProductCategoryQuantity(Category category, int quantity);
}
