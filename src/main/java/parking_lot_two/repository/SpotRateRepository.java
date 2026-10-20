package parking_lot_two.repository;

import parking_lot_two.model.SpotType;

import java.util.HashMap;
import java.util.Map;

public interface SpotRateRepository {

    Integer getRate(SpotType spotType);
}
