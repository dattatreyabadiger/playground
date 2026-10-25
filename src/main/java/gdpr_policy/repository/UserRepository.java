package gdpr_policy.repository;

import gdpr_policy.model.User;

import java.time.Clock;
import java.time.temporal.ChronoUnit;

public interface UserRepository {

    void addUser(User user);

    void removeUser(int UserId);

    void updateLastLoggedAt(int userId, Clock clock);

    void removeUsers(int unit, ChronoUnit chronoUnit, Clock clock);

    User getUser(int userId);
}
