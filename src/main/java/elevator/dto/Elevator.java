package elevator.dto;

import lombok.Data;

import java.util.Map;

@Data
public class Elevator {

    Map<Integer, Floor> floors = Map.of(0, new Floor(0, new Lift()),
                                        1, new Floor(1, null));

    int currentFloor = 0;
}
