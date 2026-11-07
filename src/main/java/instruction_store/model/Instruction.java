package instruction_store.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Instruction {

    private int id;
    private final String version;
    private InstructionStatus status;
    private final String instruction;

    public Instruction(String version, String instruction) {
        this.version = version;
        this.status = InstructionStatus.DRAFT;
        this.instruction = instruction;
    }
}
