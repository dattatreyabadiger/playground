package gdpr_policy.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class User {

    private final int id;
    private final String name;

    @Setter
    private Instant lastLoggedAt;

    private final static AtomicInteger idGenerator = new AtomicInteger();

    public User(String name) {
        this.id = idGenerator.incrementAndGet();
        this.name = name;
        this.lastLoggedAt = Instant.now();
    }
}
