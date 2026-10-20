package parking_lot.model;

import parking_lot.model.vehicle.Vehicle;

import java.util.concurrent.atomic.AtomicInteger;

public class ParkingSpot {

    private static final AtomicInteger spotIdGenerator = new AtomicInteger();

    private int id;
    private SpotType spotType;
    private SpotStatus status;
    private Vehicle parkedVehicle;

    public ParkingSpot(SpotType spotType) {
        spotIdGenerator.incrementAndGet();
        this.id = spotIdGenerator.intValue();
        this.spotType = spotType;
        this.status = SpotStatus.AVAILABLE;
    }

    public SpotType getSpotType() {
        return this.spotType;
    }

    public SpotStatus getStatus() {
        return this.status;
    }

    public boolean parkVehicle(Vehicle vehicle) {
        if (this.spotType == vehicle.getPreferredSpotType()
                && this.status == SpotStatus.AVAILABLE) {
            this.parkedVehicle = vehicle;
            this.status = SpotStatus.OCCUPIED;
            return true;
        }
        return false;
    }

    public void unparkVehicle() {
        this.parkedVehicle = null;
        this.status = SpotStatus.AVAILABLE;
    }

}
