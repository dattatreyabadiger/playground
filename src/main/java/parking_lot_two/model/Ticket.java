package parking_lot_two.model;

import lombok.Data;
import lombok.Getter;
import parking_lot_two.model.vehicle.Vehicle;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Ticket {

    private final int id;
    private final Vehicle parkedVehicle;
    private final LocalDateTime startTime;
    private volatile LocalDateTime exitTime;

    private static final AtomicInteger idGenerator = new AtomicInteger();

    public Ticket(Vehicle vehicle) {
        this.id = idGenerator.incrementAndGet();
        this.startTime = LocalDateTime.now();
        this.parkedVehicle = vehicle;
    }

    public void markExit(LocalDateTime localDateTime) {
        this.exitTime = localDateTime;
    }

    public int getTotalHours() {
        Duration duration = Duration.between(startTime, exitTime);
        return (int) Math.ceil(duration.toSeconds() / 3600.0);
    }
}
