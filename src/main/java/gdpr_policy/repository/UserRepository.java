package gdpr_policy.repository;

import gdpr_policy.model.User;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public interface UserRepository {

    void addUser(User user);

    void removeUser(int UserId);

    void updateLastLoggedAt(int userId, Clock clock);

    void removeUsers(Instant thresholdTime);

    User getUser(int userId);
}
