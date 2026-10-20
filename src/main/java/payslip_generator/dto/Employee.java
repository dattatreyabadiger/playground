package payslip_generator.dto;

import lombok.Getter;

import java.util.List;

// DTO / VALUE OBJECT (not a GoF creational/behavioral pattern): plain data carrier passed
// into PayCalculator strategies. Lombok @Getter removes boilerplate accessors.
@Getter
public class Employee {

    private int id;
    private ContractTypeEnum contractType;
    private List<Shift> shifts;

    public Employee(int id,
                    ContractTypeEnum contractType,
                    List<Shift> shifts) {
        this.id = id;
        this.contractType = contractType;
        this.shifts = shifts;
    }
}
