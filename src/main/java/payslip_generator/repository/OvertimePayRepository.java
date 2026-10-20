package payslip_generator.repository;

import java.math.BigDecimal;

// REPOSITORY PATTERN: abstracts overtime rate/limit data access away from OverPayCalculator.
public interface OvertimePayRepository {

    public BigDecimal getOverPayPerHour();
    public int getOvertimeHoursLimit();
}
