package parking_lot;

import org.junit.jupiter.api.Test;
import parking_lot.model.vehicle.Car;
import parking_lot.model.ParkingLot;
import parking_lot.model.vehicle.Vehicle;
import parking_lot.model.PaymentType;
import parking_lot.payment.PaymentFactory;
import parking_lot.repository.InMemorySpotRateRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParkingLotServiceTest {

    private final ParkingLotService parkingLotService;

    public ParkingLotServiceTest() {
        ParkingLot parkingLot = new ParkingLot(5, 20, 10, 5);
        this.parkingLotService = new ParkingLotService(parkingLot,
                                                       new InMemorySpotRateRepository(),
                                                       new PaymentFactory());
    }

    @Test
    void parkVehicleTest_successFlow() throws InterruptedException {
        Vehicle vehicle = new Car("11");
        assertTrue(parkingLotService.parkVehicle(vehicle));

        Thread.sleep(10000);
        parkingLotService.exitVehicle(vehicle);
        int fare = parkingLotService.calculateFare(vehicle);
        assertEquals(20, fare);
        parkingLotService.chargeAmount(PaymentType.CASH, fare);
    }
}
