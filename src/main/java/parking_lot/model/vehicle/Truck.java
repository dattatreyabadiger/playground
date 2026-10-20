package parking_lot.model.vehicle;

import parking_lot.model.SpotType;

// Concrete Vehicle subtype fixing its preferred SpotType (see Vehicle.java note).
public class Truck extends Vehicle {

    public Truck(String plateNumber) {
        super(plateNumber, SpotType.LARGE);
    }
}
