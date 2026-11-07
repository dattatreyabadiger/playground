package instruction_store.repository;

import instruction_store.model.Instruction;

import java.util.Set;

public interface InstructionRepository {

    int addInstruction(Instruction instruction);

    void addInstruction(int instructionId, Instruction instruction);

    void activateInstruction(int instructionId, String version);

    Instruction getInstruction(int instructionId, String version);

    Set<Instruction> getInstructions(String prefix);
}
