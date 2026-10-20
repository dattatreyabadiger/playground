package parking_lot_two;

import org.junit.jupiter.api.Test;
import parking_lot_two.model.vehicle.Bus;
import parking_lot_two.model.vehicle.Vehicle;
import parking_lot_two.repository.InMemoryParkingLotRepository;
import parking_lot_two.service.IParkingService;
import parking_lot_two.service.ParkingService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParkingServiceTest {

    private final IParkingService parkingService;

    public ParkingServiceTest() {
        this.parkingService = new ParkingService(new InMemoryParkingLotRepository(3, 100, 50, 5));
    }

    @Test
    public void parkVehicleTest() {
        Bus bus = new Bus("BH123");
        assertTrue(parkingService.parkVehicle(bus));
    }

    @Test
    public void parkVehicleTest_whenSpotUnavailable() {
        for (int i = 0; i < 15; i++) {
            Bus bus = new Bus(String.valueOf(i));
            assertTrue(parkingService.parkVehicle(bus));
        }

        Bus bus = new Bus("BH123");
        assertFalse(parkingService.parkVehicle(bus));
    }

    @Test
    public void unparkVehicleTest() {
        Bus bus = new Bus("BH123");
        assertTrue(parkingService.parkVehicle(bus));

        assertTrue(parkingService.unparkVehicle(bus));

        Bus bus2 = new Bus("BH1234");
        assertFalse(parkingService.unparkVehicle(bus2));
    }

}
