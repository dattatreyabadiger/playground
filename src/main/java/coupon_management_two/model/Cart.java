package coupon_management_two.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Cart {

    private static final AtomicInteger idGenerator = new AtomicInteger();

    private final int id;
    private final List<CartItem> cartItems;

    public Cart(List<CartItem> cartItems) {
        this.id = idGenerator.incrementAndGet();
        this.cartItems = cartItems;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (CartItem item : cartItems) {
            BigDecimal itemTotalPrice = item.getUnitPrice()
                                            .multiply(BigDecimal.valueOf(item.getQuantity()));
            totalPrice = totalPrice.add(itemTotalPrice);
        }
        return totalPrice;
    }
}
