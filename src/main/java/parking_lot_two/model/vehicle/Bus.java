package parking_lot_two.model.vehicle;

import parking_lot_two.model.SpotType;

public class Bus extends Vehicle {

    public Bus(String vehicleNumber) {
        super(vehicleNumber, SpotType.LARGE);
    }
}
