package gdpr_policy.repository;

import gdpr_policy.model.User;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class InMemoryUserRepository implements UserRepository {

    private final Map<Integer, User> userMap;
    private final TreeMap<Instant, Set<Integer>> lastLoggedInToUserIdsMap;

    public InMemoryUserRepository() {
        this.userMap = new HashMap<>();
        this.lastLoggedInToUserIdsMap = new TreeMap<>();
    }

    @Override
    public synchronized void addUser(User user) {
        userMap.put(user.getId(), user);
        lastLoggedInToUserIdsMap.computeIfAbsent(user.getLastLoggedAt(), _ -> new HashSet<>())
                                .add(user.getId());
    }

    @Override
    public synchronized void removeUser(int userId) {
        User user = getUser(userId);
        userMap.remove(userId);
        removeFromInvertedMap(userId, user.getLastLoggedAt());
    }

    @Override
    public synchronized void updateLastLoggedAt(int userId, Clock clock) {
        User user = getUser(userId);
        if (user == null)
            throw new NoSuchElementException("User Not Found");
        Instant lastLoggedAt = user.getLastLoggedAt();
        // remove user from new instant
        removeFromInvertedMap(userId, lastLoggedAt);

        // add user to new instant
        user.setLastLoggedAt(clock.instant());
        addUser(user);
    }

    private void removeFromInvertedMap(int userId, Instant lastLoggedAt) {
        Set<Integer> userIds = lastLoggedInToUserIdsMap.get(lastLoggedAt);
        if (userIds == null)
            return;
        userIds.remove(userId);

        if (userIds.isEmpty())
            lastLoggedInToUserIdsMap.remove(lastLoggedAt);
    }

    @Override
    public synchronized void removeUsers(Instant thresholdTime) {
        Set<Integer> expirableUserIds = lastLoggedInToUserIdsMap.headMap(thresholdTime, true)
                                                                .values()
                                                                .stream()
                                                                .flatMap(Collection::stream)
                                                                .collect(Collectors.toSet());

        for (int userId : expirableUserIds) {
            removeUser(userId);
        }
    }

    @Override
    public synchronized User getUser(int userId) {
        return userMap.get(userId);
    }
}
