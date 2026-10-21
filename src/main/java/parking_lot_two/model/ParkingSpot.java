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

    // park and unpark are synchronized so that no vehicles park in same spot
    // or spot-vehicle clearing during unpark will not race with spot-vehicle assignment during parking
    public synchronized boolean parkVehicle(Vehicle vehicle) {
        if (this.status != ParkingStatus.AVAILABLE)
            return false;

        this.status = ParkingStatus.OCCUPIED;
        this.vehicle = vehicle;
        return true;
    }

    public synchronized boolean unparkVehicle() {
        if (this.status != ParkingStatus.OCCUPIED)
            return false;

        this.status = ParkingStatus.AVAILABLE;
        this.vehicle = null;
        return true;
    }
}
