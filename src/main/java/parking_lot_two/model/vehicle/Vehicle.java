package parking_lot_two.model.vehicle;

import lombok.Data;
import parking_lot_two.model.SpotType;

@Data
public abstract class Vehicle {

    private final String vehicleNumber;
    private final SpotType spotType;

    public Vehicle(String vehicleNumber, SpotType spotType) {
        this.vehicleNumber = vehicleNumber;
        this.spotType = spotType;
    }
}
