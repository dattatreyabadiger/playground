package coupon_management_two.discount;

import coupon_management_two.model.Cart;

import java.math.BigDecimal;

public interface DiscountStrategy {

    BigDecimal getFinalPrice(Cart cart);
}
