package elevator;

import elevator.dto.Elevator;
import elevator.dto.Lift;
import elevator.repository.InMemoryRepository;
import elevator.service.ElevatorService;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElevatorServiceTest {

    private final ElevatorService elevatorService;

    public ElevatorServiceTest() {
        this.elevatorService = new ElevatorService(new Elevator(), new Lift(), new InMemoryRepository());
    }

    @Test
    public void testDisplayCurrentFloor() {
        int floorNumber = elevatorService.displayCurrentFloor();
        assertEquals(0, floorNumber);
    }

    @Test
    public void testListFloors() {
        Set<Integer> floors = elevatorService.listFloors();
        assertEquals(Set.of(0, 1), floors);
    }

    @Test
    public void testRequestLift() {
        String msg = elevatorService.requestLift(1);
        assertEquals("Lift will be reaching your floor", msg);

        int floorNumber = elevatorService.displayCurrentFloor();
        assertEquals(1, floorNumber);
    }

    @Test
    public void testRequestLift_liftInSameFloor() {
        String msg = elevatorService.requestLift(0);
        assertEquals("Lift is in same floor", msg);

        int floorNumber = elevatorService.displayCurrentFloor();
        assertEquals(0, floorNumber);
    }
}
