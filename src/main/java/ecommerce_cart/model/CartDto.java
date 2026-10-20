package ecommerce_cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class CartDto {

    int id;
    Map<Integer, CartItemDto> items;
    CartStatusEnum status;

    public CartDto(int id) {
        this.id = id;
        items = new HashMap<>();
        status = CartStatusEnum.IN_PROGRESS;
    }

    public BigDecimal getTotalCartPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItemDto item : items.values()) {
            BigDecimal currItemTotal = item.price.multiply(BigDecimal.valueOf(item.getQuantity()));
            totalPrice = totalPrice.add(currItemTotal);
        }
        return totalPrice;
    }
}
