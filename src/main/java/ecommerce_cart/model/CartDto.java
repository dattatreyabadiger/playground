package ecommerce_cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class CartDto {

    int id;
    Map<Integer, ItemDto> items;
    CartStatusEnum status;

    public CartDto(int id) {
        this.id = id;
        items = new HashMap<>();
        status = CartStatusEnum.IN_PROGRESS;
    }

    public BigInteger getTotalCartPrice() {
        BigInteger totalPrice = new BigInteger(String.valueOf(0));
        for (ItemDto item : items.values()) {
            totalPrice = totalPrice.add(item.price.multiply(BigInteger.valueOf(item.getQuantity())));
        }
        return totalPrice;
    }
}
