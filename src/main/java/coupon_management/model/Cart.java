package coupon_management.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Cart {

    private int id;

    private List<CartItem> cartItemList;

    private BigDecimal totalPrice;

    private static final AtomicInteger idGenerator = new AtomicInteger();


    public Cart(List<CartItem> cartItemList) {
        this.id = idGenerator.incrementAndGet();
        this.cartItemList = cartItemList;
        this.totalPrice = getCartTotalPrice();
    }

    private BigDecimal getCartTotalPrice() {
        BigDecimal price = new BigDecimal(0);
        for (CartItem cartItem : cartItemList) {
            BigDecimal itemTotalPrice = cartItem.getUnitPrice()
                                                .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            price = price.add(itemTotalPrice);
        }
        return price;
    }
}
