package payslip_generator.repository;

import java.math.BigDecimal;

// REPOSITORY PATTERN (concrete): in-memory implementation of OvertimePayRepository.
public class InMemoryOvertimePayRepository implements OvertimePayRepository {


    private static final BigDecimal overPayPerHour = BigDecimal.valueOf(20);
    private static final int OVERTIME_PAY_HOURS_LIMIT = 10;

    @Override
    public BigDecimal getOverPayPerHour() {
        return overPayPerHour;
    }

    @Override
    public int getOvertimeHoursLimit() {
        return OVERTIME_PAY_HOURS_LIMIT;
    }
}
