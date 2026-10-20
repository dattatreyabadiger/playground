package parking_lot_two.model.vehicle;

import parking_lot_two.model.SpotType;

public class Bike extends Vehicle {

    public Bike(String vehicleNumber) {
        super(vehicleNumber, SpotType.SMALL);
    }
}
