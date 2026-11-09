package cart_restriction.model;

import lombok.Getter;

@Getter
public class CartItem {

    private final int id;
    private final Category category;
    private final int quantity;

    public CartItem(int id, Category category, int quantity) {
        this.id = id;
        this.category = category;
        this.quantity = quantity;
    }
}
