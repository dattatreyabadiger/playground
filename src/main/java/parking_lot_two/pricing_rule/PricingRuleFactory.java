package parking_lot_two.pricing_rule;

import parking_lot_two.model.PricingRuleEnum;
import parking_lot_two.repository.PricingRuleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PricingRuleFactory {

    private final Map<PricingRuleEnum, PricingRule> pricingRuleFactoryMap;
    private final PricingRuleRepository pricingRuleRepository;

    public PricingRuleFactory(PricingRuleRepository pricingRuleRepository) {
        this.pricingRuleRepository = pricingRuleRepository;
        this.pricingRuleFactoryMap = Map.of(PricingRuleEnum.WEEKEND, new WeekendPricingRule(pricingRuleRepository),
                                            PricingRuleEnum.BUSY_HOURS, new BusyHoursPricingRule(pricingRuleRepository));
    }

    public PricingRule getPricingRule(PricingRuleEnum pricingRuleEnum) {
        return pricingRuleFactoryMap.get(pricingRuleEnum);
    }

    public List<PricingRule> getPricingRules() {
        return new ArrayList<>(pricingRuleFactoryMap.values());
    }
}
