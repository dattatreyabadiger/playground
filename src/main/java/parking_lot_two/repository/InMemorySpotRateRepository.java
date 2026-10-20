package parking_lot_two.repository;

import parking_lot_two.model.SpotType;

import java.util.HashMap;
import java.util.Map;

public class InMemorySpotRateRepository implements SpotRateRepository {

    private final Map<SpotType, Integer> spotRateMap = new HashMap<>();

    public InMemorySpotRateRepository() {
        this.spotRateMap.put(SpotType.SMALL, 100);
        this.spotRateMap.put(SpotType.MEDIUM, 150);
        this.spotRateMap.put(SpotType.LARGE, 200);
    }

    public Integer getRate(SpotType spotType) {
        return spotRateMap.get(spotType);
    }
}
