package parking_lot_two.model;

import lombok.AllArgsConstructor;
import parking_lot_two.model.vehicle.Vehicle;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class ParkingLevel {

    private Map<SpotType, List<ParkingSpot>> parkingSpotMap;

    public Optional<ParkingSpot> parkVehicle(Vehicle vehicle) {
        for (ParkingSpot parkingSpot : parkingSpotMap.get(vehicle.getSpotType())) {
            boolean isVehicleParked = parkingSpot.parkVehicle(vehicle);
            if (isVehicleParked)
                return Optional.of(parkingSpot);
        }
        return Optional.empty();
    }
}
