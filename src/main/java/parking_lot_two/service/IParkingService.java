package parking_lot_two.service;

import parking_lot_two.model.PricingRuleEnum;
import parking_lot_two.model.Ticket;
import parking_lot_two.model.payment.PaymentType;
import parking_lot_two.model.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.util.Set;

public interface IParkingService {

    Ticket parkVehicle(Vehicle vehicle);

    boolean unparkVehicle(Vehicle vehicle, LocalDateTime localDateTime);

    int calculateFare(Ticket ticket);

    void chargeAmount(PaymentType paymentType, int amount);
}
