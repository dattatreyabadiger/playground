package elevator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Floor {

    int floorNumber;
    Lift lift;
}
