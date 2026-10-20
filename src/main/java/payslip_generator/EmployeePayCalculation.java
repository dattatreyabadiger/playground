package payslip_generator;

import lombok.AllArgsConstructor;
import payslip_generator.calculator.PayCalculatorFactory;
import payslip_generator.dto.Employee;
import payslip_generator.dto.PayCalculatorType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// CLIENT / STRATEGY-AGGREGATION: consumes the PayCalculatorFactory (constructor-injected) to
// resolve each requested PayCalculatorType to its Strategy and sums the results per employee,
// letting STANDARD_PAY + OVERTIME_PAY (etc.) combine additively without knowing concrete calculators.
@AllArgsConstructor
public class EmployeePayCalculation {

    private final PayCalculatorFactory payCalculatorFactory;

//    public EmployeePayCalculation(PayCalculatorFactory payCalculatorFactory) {
//        this.payCalculatorFactory = payCalculatorFactory;
//    }

    public Map<Integer, BigDecimal> calculatePay(List<Employee> employees, List<PayCalculatorType> payCalculatorTypes) {
        Map<Integer, BigDecimal> employeeIdToPayMap = new HashMap<>();
        for (Employee employee : employees) {
            BigDecimal totalPay = BigDecimal.ZERO;
            for (PayCalculatorType calculatorType : payCalculatorTypes) {
                totalPay = totalPay.add(payCalculatorFactory.calculatePay(calculatorType, employee));
            }
            employeeIdToPayMap.put(employee.getId(), totalPay);
        }
        return employeeIdToPayMap;
    }
}
