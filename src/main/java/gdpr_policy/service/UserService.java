package gdpr_policy.service;

import gdpr_policy.model.User;
import gdpr_policy.repository.UserRepository;

import java.time.Clock;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class UserService implements IUserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(String name, Clock clock) {
        User user = new User(name);
        userRepository.addUser(user);
        return user;
    }

    @Override
    public User getUser(int userId) {
        return userRepository.getUser(userId);
    }

    @Override
    public void updateLastLoggedAt(int userId, Clock clock) {
        userRepository.updateLastLoggedAt(userId, clock);
    }

    @Override
    public void removeUser(int userId) {
        userRepository.removeUser(userId);
    }

    @Override
    public void removeUsersByTime(int unit, ChronoUnit chronoUnit, Clock clock) {
        if (unit <= 0)
            throw new IllegalArgumentException("Time Unit for calculating threshold time should be positive");
        Instant thresholdTime = ZonedDateTime.now(clock)
                                             .minus(unit, chronoUnit)
                                             .toInstant();
        userRepository.removeUsers(thresholdTime);
    }
}
