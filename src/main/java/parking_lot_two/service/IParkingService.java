package parking_lot_two.service;

import parking_lot_two.model.Ticket;
import parking_lot_two.model.payment.PaymentType;
import parking_lot_two.model.vehicle.Vehicle;

public interface IParkingService {

    Ticket parkVehicle(Vehicle vehicle);

    boolean unparkVehicle(Vehicle vehicle);

    int calculateFare(Ticket ticket);

    void chargeAmount(PaymentType paymentType, int amount);
}
