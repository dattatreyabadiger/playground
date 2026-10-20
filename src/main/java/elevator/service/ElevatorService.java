package elevator.service;

import elevator.dto.Elevator;
import elevator.dto.Floor;
import elevator.dto.Lift;
import elevator.dto.LiftStatusEnum;
import elevator.repository.InMemoryRepository;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class ElevatorService {

    private Elevator elevator;
    private Lift lift;
    private InMemoryRepository inMemoryRepository;

    public int displayCurrentFloor() {
        return elevator.getCurrentFloor();
    }

    public Set<Integer> listFloors() {
        return elevator.getFloors()
                       .keySet();
    }

    public String requestLift(int floorNumber) {
        Floor floor = inMemoryRepository.getFloor(elevator, floorNumber);
        if (floor == null) {
            String msg = "Invalid Floor";
            System.out.println(msg);
            return msg;
        }

        if (floor.getLift() != null) {
            String msg = "Lift is in same floor";
            System.out.println(msg);
            return msg;
        }

        if (lift.getLiftStatus() == LiftStatusEnum.OPEN) {
            String msg = "Lift is in open state. Please wait";
            System.out.println(msg);
            return msg;
        }

        floor.setLift(new Lift());
        Floor prevFloor = inMemoryRepository.getFloor(elevator, elevator.getCurrentFloor());
        prevFloor.setLift(null);
        elevator.setCurrentFloor(floorNumber);

        return "Lift will be reaching your floor";
    }

}
