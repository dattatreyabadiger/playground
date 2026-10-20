package ecommerce_cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class ItemDto {

    int id;
    String name;
    int quantity;
    BigInteger price;
}
