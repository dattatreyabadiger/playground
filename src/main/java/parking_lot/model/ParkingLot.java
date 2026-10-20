package parking_lot.model;


import parking_lot.model.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// DELEGATION HIERARCHY (not a true GoF Composite): ParkingLot -> ParkingFloor -> ParkingSpot
// delegate parkVehicle/unparkVehicle calls down the chain, but there's no shared Component
// interface treating an individual spot and an aggregate (floor/lot) uniformly, so this is
// plain composition/delegation rather than the strict Composite pattern.
public class ParkingLot {

    private List<ParkingFloor> parkingFloors;
    private Map<Vehicle, ParkingFloor> vehicleToParkingFloorMap;

    public ParkingLot(int floorCount, int smallSpots, int mediumSpots, int largeSpots) {
        this.parkingFloors = new ArrayList<>();
        this.vehicleToParkingFloorMap = new HashMap<>();

        for (int i = 0; i < floorCount; i++) {
            this.parkingFloors.add(new ParkingFloor(smallSpots, mediumSpots, largeSpots));
        }
    }

    public boolean parkVehicle(Vehicle vehicle) {
        for (ParkingFloor parkingFloor : parkingFloors) {
            boolean isParked = parkingFloor.parkVehicle(vehicle);
            if (isParked) {
                vehicleToParkingFloorMap.put(vehicle, parkingFloor);
                return true;
            }
        }
        return false;
    }

    public void unparkVehicle(Vehicle vehicle) {
        ParkingFloor parkingFloor = vehicleToParkingFloorMap.get(vehicle);
        parkingFloor.unparkVehicle(vehicle);
    }

}
