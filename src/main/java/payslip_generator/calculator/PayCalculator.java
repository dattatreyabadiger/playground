package payslip_generator.calculator;

import payslip_generator.dto.Employee;

import java.math.BigDecimal;

// STRATEGY PATTERN (interface): declares one interchangeable pay-calculation algorithm.
// StandardPayCalculator and OverPayCalculator are concrete strategies plugged in via this contract.
public interface PayCalculator {

    public BigDecimal calculatePay(Employee employee);
}
