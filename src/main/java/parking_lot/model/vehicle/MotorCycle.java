package parking_lot.model.vehicle;

import parking_lot.model.SpotType;

// Concrete Vehicle subtype fixing its preferred SpotType (see Vehicle.java note).
public class MotorCycle extends Vehicle {

    public MotorCycle(String plateNumber) {
        super(plateNumber, SpotType.SMALL);
    }
}
