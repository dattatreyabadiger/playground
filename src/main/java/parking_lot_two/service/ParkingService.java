package parking_lot_two.service;

import parking_lot_two.model.Ticket;
import parking_lot_two.model.payment.PaymentFactory;
import parking_lot_two.model.payment.PaymentType;
import parking_lot_two.model.vehicle.Vehicle;
import parking_lot_two.model.ParkingLot;
import parking_lot_two.repository.ParkingLotRepository;
import parking_lot_two.repository.SpotRateRepository;

import java.util.HashMap;
import java.util.Map;

public class ParkingService implements IParkingService {

    private final ParkingLotRepository parkingLotRepository;
    private final SpotRateRepository spotRateRepository;
    private final PaymentFactory paymentFactory;
    private final Map<Vehicle, Ticket> vehicleTicketMap;

    public ParkingService(ParkingLotRepository parkingLotRepository,
                          SpotRateRepository spotRateRepository,
                          PaymentFactory paymentFactory) {
        this.parkingLotRepository = parkingLotRepository;
        this.spotRateRepository = spotRateRepository;
        this.paymentFactory = paymentFactory;
        this.vehicleTicketMap = new HashMap<>();
    }

    @Override
    public Ticket parkVehicle(Vehicle vehicle) {
        ParkingLot parkingLot = parkingLotRepository.getParkingLot();
        Ticket ticket = new Ticket(vehicle);

        if (!parkingLot.parkVehicle(vehicle))
            return null;

        vehicleTicketMap.put(vehicle, ticket);
        return ticket;
    }

    @Override
    public boolean unparkVehicle(Vehicle vehicle) {
        ParkingLot parkingLot = parkingLotRepository.getParkingLot();

        if (!parkingLot.unparkVehicle(vehicle))
            return false;

        Ticket ticket = vehicleTicketMap.get(vehicle);
        ticket.markExit();

        vehicleTicketMap.remove(vehicle);
        return true;
    }

    @Override
    public int calculateFare(Ticket ticket) {
        int totalHours = ticket.getTotalHours();
        return totalHours * spotRateRepository.getRate(ticket.getParkedVehicle()
                                                             .getSpotType());
    }

    @Override
    public void chargeAmount(PaymentType paymentType, int amount) {
        paymentFactory.chargeAmount(paymentType, amount);
    }
}
