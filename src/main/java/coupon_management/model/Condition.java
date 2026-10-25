package coupon_management.model;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Condition {

    private final BigDecimal minCartValue;

    public Condition(BigDecimal minCartValue) {
        this.minCartValue = minCartValue;
    }
}
