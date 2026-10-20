package parking_lot_two.model;

import lombok.AllArgsConstructor;
import parking_lot_two.model.vehicle.Vehicle;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ParkingLot {

    private List<ParkingLevel> parkingLevels;

    public Optional<ParkingSpot> parkVehicle(Vehicle vehicle) {
        for (ParkingLevel parkingLevel : parkingLevels) {
            Optional<ParkingSpot> assignedSpot = parkingLevel.parkVehicle(vehicle);
            if (assignedSpot.isPresent())
                return assignedSpot;
        }
        return Optional.empty();
    }
}
