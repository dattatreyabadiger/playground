package parking_lot_two;

import org.junit.jupiter.api.Test;
import parking_lot_two.model.Ticket;
import parking_lot_two.model.payment.PaymentFactory;
import parking_lot_two.model.payment.PaymentType;
import parking_lot_two.model.vehicle.Bus;
import parking_lot_two.repository.InMemoryParkingLotRepository;
import parking_lot_two.repository.InMemorySpotRateRepository;
import parking_lot_two.service.IParkingService;
import parking_lot_two.service.ParkingService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParkingServiceTest {

    private final IParkingService parkingService;

    public ParkingServiceTest() {
        this.parkingService = new ParkingService(new InMemoryParkingLotRepository(3, 100, 50, 5),
                                                 new InMemorySpotRateRepository(),
                                                 new PaymentFactory());
    }

    @Test
    public void parkVehicleTest() {
        Bus bus = new Bus("BH123");
        assertNotNull(parkingService.parkVehicle(bus));
    }

    @Test
    public void parkVehicleTest_whenSpotUnavailable() {
        for (int i = 0; i < 15; i++) {
            Bus bus = new Bus(String.valueOf(i));
            assertNotNull(parkingService.parkVehicle(bus));
        }

        Bus bus = new Bus("BH123");
        assertNull(parkingService.parkVehicle(bus));
    }

    @Test
    public void unparkVehicleTest() {
        Bus bus = new Bus("BH123");
        assertNotNull(parkingService.parkVehicle(bus));

        assertTrue(parkingService.unparkVehicle(bus));

        Bus bus2 = new Bus("BH1234");
        assertFalse(parkingService.unparkVehicle(bus2));
    }

    @Test
    public void calculateFareTest() throws InterruptedException {
        Bus bus = new Bus("BH123");
        Ticket ticket = parkingService.parkVehicle(bus);
        assertNotNull(ticket);

        // simulate unpark after few seconds
        Thread.sleep(2000);
        assertTrue(parkingService.unparkVehicle(bus));

        assertEquals(200, parkingService.calculateFare(ticket));

        assertDoesNotThrow(() -> parkingService.chargeAmount(PaymentType.CASH, 100));
    }

}
