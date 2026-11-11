package cart_restriction_two.model;

import lombok.Getter;

@Getter
public class CartItem {

    private final int id;
    private final Category category;
    private final int quantity;

    public CartItem(int id, Category category, int quantity) {
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity should be positive");

        this.id = id;
        this.category = category;
        this.quantity = quantity;
    }
}
