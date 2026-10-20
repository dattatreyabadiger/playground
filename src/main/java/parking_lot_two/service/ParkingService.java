package parking_lot_two.service;

import parking_lot_two.model.ParkingSpot;
import parking_lot_two.model.vehicle.Vehicle;
import parking_lot_two.model.ParkingLot;
import parking_lot_two.repository.ParkingLotRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ParkingService implements IParkingService {

    private final ParkingLotRepository parkingLotRepository;
    private final Map<Vehicle, ParkingSpot> vehicleToSpotMap;

    public ParkingService(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
        vehicleToSpotMap = new HashMap<>();
    }

    @Override
    public boolean parkVehicle(Vehicle vehicle) {
        ParkingLot parkingLot = parkingLotRepository.getParkingLot();
        Optional<ParkingSpot> assignedSpot = parkingLot.parkVehicle(vehicle);
        if (assignedSpot.isEmpty())
            return false;

        vehicleToSpotMap.put(vehicle, assignedSpot.get());
        return true;
    }

    @Override
    public boolean unparkVehicle(Vehicle vehicle) {
        ParkingSpot parkingSpot = vehicleToSpotMap.get(vehicle);
        if (parkingSpot == null)
            return false;

        boolean isVehicleUnparked = parkingSpot.unparkVehicle();
        if (!isVehicleUnparked)
            return false;

        vehicleToSpotMap.remove(vehicle);
        return true;
    }
}
