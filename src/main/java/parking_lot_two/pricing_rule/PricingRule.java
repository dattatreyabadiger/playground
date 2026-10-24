package parking_lot_two.pricing_rule;


import java.time.LocalDateTime;

// strategy here doesn't make more sense, as it calls repository again
// if we have logic of defining if it is weekend or busy hour in code, then it is good to have strategy
public interface PricingRule {

    int getSurchargePercentage(LocalDateTime localDateTime);
}
