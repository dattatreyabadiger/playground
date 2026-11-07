package instruction_store.repository;

import instruction_store.model.Instruction;
import instruction_store.model.InstructionStatus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryInstructionRepository implements InstructionRepository {

    private static final AtomicInteger idGenerator = new AtomicInteger();

    private final Map<Integer, Set<String>> instructionIdToVersionsMap;
    private final Map<Integer, String> instructionIdToActiveVersionMap;
    private final Map<String, Instruction> versionToInstructionMap;
    private final Map<String, Set<String>> prefixToVersionsMap;

    public InMemoryInstructionRepository() {
        this.instructionIdToVersionsMap = new HashMap<>();
        this.instructionIdToActiveVersionMap = new HashMap<>();
        this.versionToInstructionMap = new HashMap<>();
        this.prefixToVersionsMap = new HashMap<>();
    }

    @Override
    public int addInstruction(Instruction instruction) {
        int instructionId = idGenerator.incrementAndGet();
        persistInstruction(instructionId, instruction);

        return instructionId;
    }

    @Override
    public void addInstruction(int instructionId, Instruction instruction) {
        persistInstruction(instructionId, instruction);
    }

    private void persistInstruction(int instructionId, Instruction instruction) {
        instruction.setId(instructionId);
        String version = instruction.getVersion();
        instructionIdToVersionsMap.computeIfAbsent(instructionId, k -> new HashSet<>())
                                  .add(version);
        versionToInstructionMap.put(version, instruction);

        String instructionPrefix = instruction.getInstruction()
                                              .trim()
                                              .split("\\s+")[0];
        prefixToVersionsMap.computeIfAbsent(instructionPrefix, k -> new HashSet<>())
                           .add(version);
    }

    @Override
    public void activateInstruction(int instructionId, String version) {
        Set<String> allVersions = instructionIdToVersionsMap.get(instructionId);
        if (allVersions == null)
            throw new IllegalArgumentException("Instruction does not exist");

        if (!allVersions.contains(version))
            throw new IllegalArgumentException("Version does not exist for the instruction");

        String currentActiveVersion = instructionIdToActiveVersionMap.get(instructionId);
        if (currentActiveVersion != null) {
            Instruction currentActiveInstruction = versionToInstructionMap.get(currentActiveVersion);
            currentActiveInstruction.setStatus(InstructionStatus.ARCHIVED);
        }

        instructionIdToActiveVersionMap.put(instructionId, version);
    }

    @Override
    public Instruction getInstruction(int instructionId, String version) {
        Set<String> allVersions = instructionIdToVersionsMap.get(instructionId);
        if (allVersions == null)
            throw new IllegalArgumentException("Instruction does not exist");

        if (!allVersions.contains(version))
            throw new IllegalArgumentException("Version does not exist for the instruction");

        return versionToInstructionMap.get(version);
    }

    @Override
    public Set<Instruction> getInstructions(String prefix) {
        Set<String> versions = prefixToVersionsMap.get(prefix);
        if (versions == null)
            throw new IllegalArgumentException("No Instruction exists with this prefix");

        Set<Instruction> instructions = new HashSet<>();
        for (String version : versions) {
            instructions.add(versionToInstructionMap.get(version));
        }
        return instructions;
    }
}
