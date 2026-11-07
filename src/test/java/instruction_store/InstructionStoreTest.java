package instruction_store;

import instruction_store.model.Instruction;
import instruction_store.repository.InMemoryInstructionRepository;
import instruction_store.service.InstructionStoreService;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InstructionStoreTest {

    private final InstructionStoreService instructionStoreService;

    public InstructionStoreTest() {
        this.instructionStoreService = new InstructionStoreService(new InMemoryInstructionRepository());
    }

    @Test
    public void addInstructionTest() {
        Instruction instruction = new Instruction("v1", "Dining Table is very short");
        int instructionId1 = instructionStoreService.addInstruction(instruction);
        Instruction instruction2 = new Instruction("v2", "Dining Table is very cheap");
        instructionStoreService.addInstruction(instructionId1, instruction2);

        Instruction actualInstruction = instructionStoreService.getInstruction(instructionId1, "v1");
        instruction.setId(instructionId1);
        assertEquals(instruction, actualInstruction);

        Instruction actualInstruction2 = instructionStoreService.getInstruction(instructionId1, "v2");
        instruction2.setId(instructionId1);
        assertEquals(instruction2, actualInstruction2);

        instructionStoreService.activateInstruction(instructionId1, "v2");
        assertEquals(instruction, actualInstruction);
//        assertNotEquals(instruction2, actualInstruction2);
//        instruction2.setStatus(InstructionStatus.ACTIVE);
        assertEquals(instruction2, actualInstruction2);

        Set<Instruction> actualInstructions = instructionStoreService.getInstructions("Dining");
        assertEquals(Set.of(instruction, instruction2), actualInstructions);
    }
}
