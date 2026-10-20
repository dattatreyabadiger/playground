package parking_lot_two.model;

import lombok.AllArgsConstructor;
import parking_lot_two.model.vehicle.Vehicle;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class ParkingLot {

    private List<ParkingLevel> parkingLevels;
    private final Map<Vehicle, ParkingSpot> vehicleToSpotMap;

    public boolean parkVehicle(Vehicle vehicle) {
        for (ParkingLevel parkingLevel : parkingLevels) {
            Optional<ParkingSpot> assignedSpot = parkingLevel.parkVehicle(vehicle);
            if (assignedSpot.isPresent()) {
                vehicleToSpotMap.put(vehicle, assignedSpot.get());
                return true;
            }
        }
        return false;
    }

    public boolean unparkVehicle(Vehicle vehicle) {
        ParkingSpot parkingSpot = vehicleToSpotMap.get(vehicle);
        if (parkingSpot == null)
            return false;

        parkingSpot.unparkVehicle();
        vehicleToSpotMap.remove(vehicle);
        return true;
    }
}
