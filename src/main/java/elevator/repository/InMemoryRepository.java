package elevator.repository;

import elevator.dto.Elevator;
import elevator.dto.Floor;


public class InMemoryRepository {

    public Floor getFloor(Elevator elevator, int floorNumber) {
        return elevator.getFloors()
                       .get(floorNumber);
    }
}
