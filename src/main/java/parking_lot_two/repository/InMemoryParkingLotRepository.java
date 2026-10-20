package parking_lot_two.repository;

import parking_lot_two.model.ParkingLevel;
import parking_lot_two.model.ParkingLot;
import parking_lot_two.model.ParkingSpot;
import parking_lot_two.model.SpotType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryParkingLotRepository implements ParkingLotRepository {

    private final ParkingLot parkingLot;

    public InMemoryParkingLotRepository(int levels,
                                        int smallSpotsPerLevel, 
                                        int mediumSpotsPerLevel,
                                        int largeSpotsPerLevel) {
        List<ParkingLevel> parkingLevels = new ArrayList<>();
        for (int i = 0; i < levels; i++) {
            Map<SpotType, List<ParkingSpot>> parkingSpotMap = new HashMap<>();

            parkingSpotMap.put(SpotType.SMALL, creatParkingSpots(SpotType.SMALL, smallSpotsPerLevel));
            parkingSpotMap.put(SpotType.MEDIUM, creatParkingSpots(SpotType.MEDIUM, mediumSpotsPerLevel));
            parkingSpotMap.put(SpotType.LARGE, creatParkingSpots(SpotType.LARGE, largeSpotsPerLevel));

            parkingLevels.add(new ParkingLevel(parkingSpotMap));
        }
        this.parkingLot = new ParkingLot(parkingLevels, new HashMap<>());
    }

    private List<ParkingSpot> creatParkingSpots(SpotType spotType, int spotsCount) {
        List<ParkingSpot> parkingSpots = new ArrayList<>();
        for (int j = 0; j < spotsCount; j++) {
            parkingSpots.add(new ParkingSpot(spotType));
        }
        return parkingSpots;
    }

    @Override
    public ParkingLot getParkingLot() {
        return parkingLot;
    }
}
