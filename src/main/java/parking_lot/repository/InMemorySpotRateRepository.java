package parking_lot.repository;

import parking_lot.model.SpotType;

import java.util.HashMap;
import java.util.Map;

// REPOSITORY PATTERN (concrete): in-memory implementation of SpotRateRepository.
public class InMemorySpotRateRepository implements SpotRateRepository {

    private final Map<SpotType, Integer> spotRateMap;

    public InMemorySpotRateRepository() {
        spotRateMap = new HashMap<>();
        spotRateMap.put(SpotType.SMALL, 10);
        spotRateMap.put(SpotType.MEDIUM, 20);
        spotRateMap.put(SpotType.LARGE, 50);
    }

    @Override
    public int getRateForSpotType(SpotType spotType) {
        return spotRateMap.get(spotType);
    }
}
