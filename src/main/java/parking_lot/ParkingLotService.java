package parking_lot;


import parking_lot.model.ParkingLot;
import parking_lot.model.Ticket;
import parking_lot.model.vehicle.Vehicle;
import parking_lot.model.PaymentType;
import parking_lot.payment.PaymentFactory;
import parking_lot.repository.SpotRateRepository;

import java.util.HashMap;
import java.util.Map;

// FACADE PATTERN: exposes a simple, unified API (parkVehicle/exitVehicle/calculateFare/
// chargeAmount) over the ParkingLot hierarchy, rate repository, and payment factory,
// hiding their internal coordination from calling code.
// Also uses CONSTRUCTOR-BASED DEPENDENCY INJECTION: collaborators are passed in rather
// than instantiated internally, decoupling this class from concrete implementations.
public class ParkingLotService {

    private final ParkingLot parkingLot;
    private final SpotRateRepository spotRateRepository;
    private final PaymentFactory paymentFactory;

    private final Map<Vehicle, Ticket> activeTickets;

    public ParkingLotService(ParkingLot parkingLot,
                             SpotRateRepository spotRateRepository,
                             PaymentFactory paymentFactory) {
        this.parkingLot = parkingLot;
        this.spotRateRepository = spotRateRepository;
        this.paymentFactory = paymentFactory;
        this.activeTickets = new HashMap<>();
    }

    public boolean parkVehicle(Vehicle vehicle) {
        boolean isParked = parkingLot.parkVehicle(vehicle);
        if (!isParked)
            return false;
        activeTickets.put(vehicle, new Ticket(vehicle));
        return true;
    }

    public void exitVehicle(Vehicle vehicle) {
        Ticket ticket = activeTickets.get(vehicle);
        ticket.markExit();
        parkingLot.unparkVehicle(vehicle);
    }

    public int calculateFare(Vehicle vehicle) {
        Ticket ticket = activeTickets.get(vehicle);
        int payForSpotType = spotRateRepository.getRateForSpotType(vehicle.getPreferredSpotType());
        return payForSpotType * ticket.getTotalHours();
    }

    public void chargeAmount(PaymentType paymentType, int amount) {
        paymentFactory.chargeAmount(paymentType, amount);
    }
}
