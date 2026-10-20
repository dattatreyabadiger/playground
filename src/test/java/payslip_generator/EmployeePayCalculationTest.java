package payslip_generator;

import org.junit.jupiter.api.Test;
import payslip_generator.calculator.PayCalculatorFactory;
import payslip_generator.dto.ContractTypeEnum;
import payslip_generator.dto.Employee;
import payslip_generator.dto.PayCalculatorType;
import payslip_generator.dto.Shift;
import payslip_generator.dto.SkillEnum;
import payslip_generator.repository.InMemoryOvertimePayRepository;
import payslip_generator.repository.InMemoryPayRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


class EmployeePayCalculationTest {

    private final PayCalculatorFactory payCalculatorFactory = new PayCalculatorFactory(new InMemoryPayRepository(), new InMemoryOvertimePayRepository());
    private final EmployeePayCalculation employeePayCalculationService = new EmployeePayCalculation(payCalculatorFactory);

    @Test
    void testCalculatePay() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, ContractTypeEnum.PERMANENT, List.of(new Shift(1, SkillEnum.BAKERY, 6, 1))));
        List<PayCalculatorType> calculatorTypes = List.of(PayCalculatorType.STANDARD_PAY, PayCalculatorType.OVERTIME_PAY);
        Map<Integer, BigDecimal> integerBigDecimalMap = employeePayCalculationService.calculatePay(employees,
                                                                                                   calculatorTypes);
        System.out.println(integerBigDecimalMap);
    }
}
