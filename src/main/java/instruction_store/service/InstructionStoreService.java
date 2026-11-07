package instruction_store.service;

import instruction_store.model.Instruction;
import instruction_store.repository.InstructionRepository;

import java.util.Set;

public class InstructionStoreService {

    private final InstructionRepository instructionRepository;

    public InstructionStoreService(InstructionRepository instructionRepository) {
        this.instructionRepository = instructionRepository;
    }

    public int addInstruction(Instruction instruction) {
        return instructionRepository.addInstruction(instruction);
    }

    public void addInstruction(int instructionId, Instruction instruction) {
        instructionRepository.addInstruction(instructionId, instruction);
    }

    public void activateInstruction(int instructionId, String version) {
        instructionRepository.activateInstruction(instructionId, version);
    }

    public Instruction getInstruction(int instructionId, String version) {
        return instructionRepository.getInstruction(instructionId, version);
    }

    public Set<Instruction> getInstructions(String prefix) {
        return instructionRepository.getInstructions(prefix);
    }
}
