package parking_lot_two.model.vehicle;

import parking_lot_two.model.SpotType;

public class Car extends Vehicle {

    public Car(String vehicleNumber) {
        super(vehicleNumber, SpotType.MEDIUM);
    }
}
