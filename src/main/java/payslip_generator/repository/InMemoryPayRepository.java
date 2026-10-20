package payslip_generator.repository;

import payslip_generator.dto.ContractTypeEnum;
import payslip_generator.dto.SkillEnum;

import java.math.BigDecimal;
import java.util.Map;

import static payslip_generator.dto.ContractTypeEnum.AGENCY;
import static payslip_generator.dto.ContractTypeEnum.PERMANENT;
import static payslip_generator.dto.ContractTypeEnum.ZERO_HOURS;
import static payslip_generator.dto.SkillEnum.BAKERY;
import static payslip_generator.dto.SkillEnum.CHECKOUT_CASHIER;
import static payslip_generator.dto.SkillEnum.CLEANING;
import static payslip_generator.dto.SkillEnum.CUSTOMER_SERVICE;
import static payslip_generator.dto.SkillEnum.DELIVERY_DRIVER;
import static payslip_generator.dto.SkillEnum.SECURITY;
import static payslip_generator.dto.SkillEnum.SHIFT_LEADER;

// REPOSITORY PATTERN (concrete): in-memory implementation of PayRepository, easily swappable
// for a persistent (e.g. JPA/DB) implementation without touching calculator logic.
public class InMemoryPayRepository implements PayRepository {

    private final Map<ContractTypeEnum, BigDecimal> contractTypeCalculationMap;
    private final Map<SkillEnum, BigDecimal> skillPayCalculationMap;


    public InMemoryPayRepository() {
        contractTypeCalculationMap = Map.of(PERMANENT, BigDecimal.valueOf(12.5), AGENCY, BigDecimal.valueOf(13),
                                            ZERO_HOURS, BigDecimal.valueOf(13));

        skillPayCalculationMap = Map.of(BAKERY, BigDecimal.valueOf(2), CHECKOUT_CASHIER, BigDecimal.valueOf(1.2),
                                        CUSTOMER_SERVICE, BigDecimal.valueOf(1.3), SHIFT_LEADER, BigDecimal.valueOf(3),
                                        SECURITY, BigDecimal.valueOf(1), CLEANING, BigDecimal.valueOf(1),
                                        DELIVERY_DRIVER, BigDecimal.valueOf(2));

    }

    @Override
    public BigDecimal getPayForContract(ContractTypeEnum contractType) {
        return contractTypeCalculationMap.get(contractType);
    }

    @Override
    public BigDecimal getPayForSkill(SkillEnum skill) {
        return skillPayCalculationMap.get(skill);
    }
}
