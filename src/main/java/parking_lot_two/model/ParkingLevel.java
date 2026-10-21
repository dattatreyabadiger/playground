package parking_lot_two.model;

import parking_lot_two.model.vehicle.Vehicle;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ParkingLevel {

    private final Map<SpotType, List<ParkingSpot>> parkingSpotMap;

    public ParkingLevel(Map<SpotType, List<ParkingSpot>> parkingSpotMap) {
        this.parkingSpotMap = parkingSpotMap;
    }

    public Optional<ParkingSpot> parkVehicle(Vehicle vehicle) {
        for (ParkingSpot parkingSpot : parkingSpotMap.get(vehicle.getSpotType())) {
            boolean isVehicleParked = parkingSpot.parkVehicle(vehicle);
            if (isVehicleParked)
                return Optional.of(parkingSpot);
        }
        return Optional.empty();
    }
}
