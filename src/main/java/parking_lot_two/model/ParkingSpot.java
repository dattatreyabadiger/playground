package parking_lot_two.model;

import parking_lot_two.model.vehicle.Vehicle;

public class ParkingSpot {

    private final SpotType spotType;
    private ParkingStatus status;
    private Vehicle vehicle;

    public ParkingSpot(SpotType spotType) {
        this.spotType = spotType;
        this.status = ParkingStatus.AVAILABLE;
    }

    public boolean parkVehicle(Vehicle vehicle) {
        if (this.status != ParkingStatus.AVAILABLE)
            return false;

        this.status = ParkingStatus.OCCUPIED;
        this.vehicle = vehicle;
        return true;
    }

    public boolean unparkVehicle() {
        if (this.status != ParkingStatus.OCCUPIED)
            return false;

        this.status = ParkingStatus.AVAILABLE;
        this.vehicle = null;
        return true;
    }
}
