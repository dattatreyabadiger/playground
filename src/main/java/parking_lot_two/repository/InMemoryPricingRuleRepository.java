package parking_lot_two.repository;

import parking_lot_two.model.PricingRuleEnum;

import java.util.Map;

public class InMemoryPricingRuleRepository implements PricingRuleRepository {

    private final Map<PricingRuleEnum, Integer> pricingRuleMap = Map.of(PricingRuleEnum.WEEKEND, 50,
                                                                        PricingRuleEnum.BUSY_HOURS, 20);

    @Override
    public int getSurchargePercentage(PricingRuleEnum pricingRule) {
        return pricingRuleMap.get(pricingRule);
    }
}
