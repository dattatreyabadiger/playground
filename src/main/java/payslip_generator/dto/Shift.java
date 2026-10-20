package payslip_generator.dto;

import lombok.Getter;

// DTO / VALUE OBJECT: plain data carrier; getWorkDuration() derives a value rather than
// storing it, keeping the DTO consistent (single source of truth: totalDuration - breakDuration).
@Getter
public class Shift {

    private int id;
    private SkillEnum skill;
    private int totalDuration;
    private int breakDuration;

    public Shift(int id,
                 SkillEnum skill,
                 int totalDuration,
                 int breakDuration) {
        this.id = id;
        this.skill = skill;
        this.totalDuration = totalDuration;
        this.breakDuration = breakDuration;
    }

    public int getWorkDuration() {
        return totalDuration - breakDuration;
    }
}
