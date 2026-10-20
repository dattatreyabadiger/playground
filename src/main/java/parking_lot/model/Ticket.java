package parking_lot.model;

import parking_lot.model.vehicle.Vehicle;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

// Domain entity (not a GoF pattern): tracks a parking session's lifecycle (entry/exit/duration).
// Uses a static AtomicInteger as a thread-safe monotonic ID generator, similar to ParkingSpot.
public class Ticket {

    private static final AtomicInteger ticketIdGenerator = new AtomicInteger();

    private int id;
    private Vehicle vehicle;
    private Instant startTime;
    private Instant exitTime;

    public Ticket(Vehicle vehicle) {
        this.id = ticketIdGenerator.incrementAndGet();
        this.vehicle = vehicle;
        this.startTime = Instant.now();
    }

    public void markExit() {
        this.exitTime = Instant.now();
    }

    public int getTotalHours() {
        Duration duration = Duration.between(startTime, exitTime);
        return (int) Math.ceil(duration.toSeconds() / 3600.0);
    }
}
