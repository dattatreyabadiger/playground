package payslip_generator.repository;

import payslip_generator.dto.ContractTypeEnum;
import payslip_generator.dto.SkillEnum;

import java.math.BigDecimal;

// REPOSITORY PATTERN: abstracts contract/skill pay-rate data access away from calculators.
// InMemoryPayRepository is the current implementation; a DB-backed one could replace it
// without changing StandardPayCalculator.
public interface PayRepository {

    public BigDecimal getPayForContract(ContractTypeEnum contractType);
    public BigDecimal getPayForSkill(SkillEnum skill);
}
