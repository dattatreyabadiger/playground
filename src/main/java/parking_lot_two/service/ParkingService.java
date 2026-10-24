package parking_lot_two.service;

import parking_lot_two.model.PricingRuleEnum;
import parking_lot_two.model.Ticket;
import parking_lot_two.model.payment.PaymentFactory;
import parking_lot_two.model.payment.PaymentType;
import parking_lot_two.model.vehicle.Vehicle;
import parking_lot_two.model.ParkingLot;
import parking_lot_two.pricing_rule.PricingRule;
import parking_lot_two.pricing_rule.PricingRuleFactory;
import parking_lot_two.repository.ParkingLotRepository;
import parking_lot_two.repository.SpotRateRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ParkingService implements IParkingService {

    private final ParkingLotRepository parkingLotRepository;
    private final SpotRateRepository spotRateRepository;
    private final PaymentFactory paymentFactory;
    private final ConcurrentMap<Vehicle, Ticket> vehicleTicketMap;
    private final PricingRuleFactory pricingRuleFactory;

    public ParkingService(ParkingLotRepository parkingLotRepository,
                          SpotRateRepository spotRateRepository,
                          PaymentFactory paymentFactory,
                          PricingRuleFactory pricingRuleFactory) {
        this.parkingLotRepository = parkingLotRepository;
        this.spotRateRepository = spotRateRepository;
        this.paymentFactory = paymentFactory;
        this.pricingRuleFactory = pricingRuleFactory;
        this.vehicleTicketMap = new ConcurrentHashMap<>();
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
    public boolean unparkVehicle(Vehicle vehicle, LocalDateTime localDateTime) {
        ParkingLot parkingLot = parkingLotRepository.getParkingLot();

        Ticket ticket = vehicleTicketMap.get(vehicle);
        if (ticket == null || !parkingLot.unparkVehicle(vehicle))
            return false;

        ticket.markExit(localDateTime);
        vehicleTicketMap.remove(vehicle);
        return true;
    }

    @Override
    public int calculateFare(Ticket ticket) {
        int surchargePercentage = 0;
        for (PricingRule pricingRule : pricingRuleFactory.getPricingRules()) {
            surchargePercentage += pricingRule.getSurchargePercentage(ticket.getExitTime());
        }
        int totalHours = ticket.getTotalHours();

        int regularRate = spotRateRepository.getRate(ticket.getParkedVehicle()
                                                           .getSpotType());
        int chargePerHourWithSurcharge = regularRate * (100 + surchargePercentage) / 100;
        return totalHours * chargePerHourWithSurcharge;
    }

    @Override
    public void chargeAmount(PaymentType paymentType, int amount) {
        paymentFactory.chargeAmount(paymentType, amount);
    }
}
