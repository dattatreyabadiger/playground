package parking_lot_two.model;

import parking_lot_two.model.vehicle.Vehicle;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ParkingLot {

    private final List<ParkingLevel> parkingLevels;

    /*
    Why ConcurrentHashMap here
    if both new keys belong in same bucket’s linked list, they can race to append after the existing second node.
    Starting state:
        bucket → node1 → node2 → null
        A possible interleaving:
        1. Thread A sees node2.next == null.
        2. Thread B also sees node2.next == null.
        3. A writes node2.next = nodeA.
        4. B writes node2.next = nodeB, overwriting A’s link.

        Final state:
        bucket → node1 → node2 → nodeB
     */
    private final ConcurrentMap<Vehicle, ParkingSpot> vehicleToSpotMap = new ConcurrentHashMap<>();

    public ParkingLot(List<ParkingLevel> parkingLevels) {
        this.parkingLevels = parkingLevels;
    }

    public boolean parkVehicle(Vehicle vehicle) {
        if (vehicleToSpotMap.containsKey(vehicle))
            return false;

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

        if (!parkingSpot.unparkVehicle())
            return false;

        vehicleToSpotMap.remove(vehicle);
        return true;
    }
}
