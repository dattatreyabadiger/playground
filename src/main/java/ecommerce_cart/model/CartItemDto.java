package ecommerce_cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CartItemDto {

    int id;
    String name;
    int quantity;
    BigDecimal price;
}
