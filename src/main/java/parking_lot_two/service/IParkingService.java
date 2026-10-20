package parking_lot_two.service;

import parking_lot_two.model.vehicle.Vehicle;

public interface IParkingService {

    boolean parkVehicle(Vehicle vehicle);

    boolean unparkVehicle(Vehicle vehicle);
}
