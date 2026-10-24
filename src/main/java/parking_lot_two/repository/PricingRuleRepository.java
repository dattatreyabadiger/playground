package parking_lot_two.repository;

import parking_lot_two.model.PricingRuleEnum;

public interface PricingRuleRepository {

    int getSurchargePercentage(PricingRuleEnum pricingRule);
}
