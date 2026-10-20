package parking_lot.model.vehicle;

import parking_lot.model.SpotType;

// TEMPLATE-STYLE INHERITANCE (not a full GoF pattern on its own): Car/MotorCycle/Truck
// only differ by the SpotType passed to the super constructor. This sets up each subtype's
// "preferred spot" once, letting ParkingFloor/ParkingSpot logic stay type-agnostic.
public abstract class Vehicle {

    private String plateNumber;
    private SpotType preferredSpotType;

    protected Vehicle(String plateNumber, SpotType spotType) {
        this.plateNumber = plateNumber;
        this.preferredSpotType = spotType;
    }

    public SpotType getPreferredSpotType() {
        return preferredSpotType;
    }
}
