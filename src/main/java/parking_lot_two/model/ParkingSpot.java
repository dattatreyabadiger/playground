package parking_lot_two.model;

import parking_lot_two.model.vehicle.Vehicle;

import java.util.concurrent.atomic.AtomicInteger;

public class ParkingSpot {

    private final int id;
    private final SpotType spotType;
    private ParkingStatus status;
    private Vehicle vehicle;

    private static final AtomicInteger idGenerator = new AtomicInteger();

    public ParkingSpot(SpotType spotType) {
        this.spotType = spotType;
        this.status = ParkingStatus.AVAILABLE;
        this.id = idGenerator.incrementAndGet();
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
