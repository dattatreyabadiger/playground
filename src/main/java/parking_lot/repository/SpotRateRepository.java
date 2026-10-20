package parking_lot.repository;

import parking_lot.model.SpotType;

// REPOSITORY PATTERN: abstracts spot pricing data access away from ParkingLotService.
// InMemorySpotRateRepository is the current implementation; a DB-backed one could replace it
// without changing the service layer.
public interface SpotRateRepository {

    int getRateForSpotType(SpotType spotType);
}
