package parking_lot.model;

import parking_lot.model.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingFloor {

    private final List<ParkingSpot> parkingSpotList;
    private final Map<Vehicle, ParkingSpot> vehicleToParkingSpotMap;

    public ParkingFloor(int smallSpots, int mediumSpots, int largeSpots) {
        this.parkingSpotList = new ArrayList<>();
        this.vehicleToParkingSpotMap = new HashMap<>();

        // add SMALL spots
        for (int i = 0; i < smallSpots; i++)
            this.parkingSpotList.add(new ParkingSpot(SpotType.SMALL));

        // add MEDIUM spots
        for (int i = 0; i < mediumSpots; i++)
            this.parkingSpotList.add(new ParkingSpot(SpotType.MEDIUM));

        // add LARGE spots
        for (int i = 0; i < largeSpots; i++)
            this.parkingSpotList.add(new ParkingSpot(SpotType.LARGE));
    }

    public boolean parkVehicle(Vehicle vehicle) {
        for (ParkingSpot parkingSpot : parkingSpotList) {
            boolean isParked = parkingSpot.parkVehicle(vehicle);
            if (isParked) {
                vehicleToParkingSpotMap.put(vehicle, parkingSpot);
                return true;
            }
        }
        return false;
    }

    public void unparkVehicle(Vehicle vehicle) {
        ParkingSpot parkingSpot = this.vehicleToParkingSpotMap.get(vehicle);
        if (parkingSpot == null)
            return;

        parkingSpot.unparkVehicle();
    }
}
