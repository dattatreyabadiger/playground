package parking_lot_two.pricing_rule;

import lombok.AllArgsConstructor;
import parking_lot_two.repository.PricingRuleRepository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static parking_lot_two.model.PricingRuleEnum.WEEKEND;

@AllArgsConstructor
public class WeekendPricingRule implements PricingRule {

    private final PricingRuleRepository pricingRuleRepository;

    @Override
    public int getSurchargePercentage(LocalDateTime localDateTime) {
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        if (dayOfWeek.equals(DayOfWeek.SUNDAY) || dayOfWeek.equals(DayOfWeek.SATURDAY))
            return pricingRuleRepository.getSurchargePercentage(WEEKEND);
        return 0;
    }
}
