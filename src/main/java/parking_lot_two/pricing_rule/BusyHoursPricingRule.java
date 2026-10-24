package parking_lot_two.pricing_rule;

import lombok.AllArgsConstructor;
import parking_lot_two.repository.PricingRuleRepository;

import java.time.LocalDateTime;

import static parking_lot_two.model.PricingRuleEnum.BUSY_HOURS;

@AllArgsConstructor
public class BusyHoursPricingRule implements PricingRule {

    private final PricingRuleRepository pricingRuleRepository;

    @Override
    public int getSurchargePercentage(LocalDateTime localDateTime) {
        int hour = localDateTime.getHour();
        if (hour >= 18 && hour <= 22)
            return pricingRuleRepository.getSurchargePercentage(BUSY_HOURS);
        return 0;
    }
}
