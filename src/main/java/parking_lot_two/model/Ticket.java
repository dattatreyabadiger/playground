package parking_lot_two.model;

import lombok.Data;
import lombok.Getter;
import parking_lot_two.model.vehicle.Vehicle;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Ticket {

    private final int id;
    private final Vehicle parkedVehicle;
    private final Instant startTime;
    private volatile Instant exitTime;

    private static final AtomicInteger idGenerator = new AtomicInteger();

    public Ticket(Vehicle vehicle) {
        this.id = idGenerator.incrementAndGet();
        this.startTime = Instant.now();
        this.parkedVehicle = vehicle;
    }

    public void markExit() {
        this.exitTime = Instant.now();
    }

    public int getTotalHours() {
        Duration duration = Duration.between(startTime, exitTime);
        return (int) Math.ceil(duration.toSeconds() / 3600.0);
    }
}
