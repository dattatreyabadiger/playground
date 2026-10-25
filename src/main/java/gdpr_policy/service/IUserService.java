package gdpr_policy.service;

import gdpr_policy.model.User;

import java.time.Clock;
import java.time.temporal.ChronoUnit;

public interface IUserService {

    User addUser(String name);

    User getUser(int userId);

    void updateLastLoggedAt(int userId, Clock clock);

    void removeUser(int userId);

    void removeUsersByTime(int unit, ChronoUnit chronoUnit, Clock clock);
}
