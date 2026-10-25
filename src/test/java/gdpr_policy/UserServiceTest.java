package gdpr_policy;

import gdpr_policy.model.User;
import gdpr_policy.repository.InMemoryUserRepository;
import gdpr_policy.service.IUserService;
import gdpr_policy.service.UserService;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserServiceTest {

    private final IUserService userService;

    public UserServiceTest() {
        this.userService = new UserService(new InMemoryUserRepository());
    }

    @Test
    public void removeUsersByTimeTest() {
        User user = userService.addUser("Datt");
        User user2 = userService.addUser("Rego");

        // refresh logic for user2
        Instant timeAfter5Days = Instant.now()
                                        .plus(5, ChronoUnit.DAYS);
        userService.updateLastLoggedAt(user2.getId(), Clock.fixed(timeAfter5Days, ZoneId.of("UTC")));

        Instant timeAfter12Days = Instant.now()
                                         .plus(12, ChronoUnit.DAYS);
        userService.removeUsersByTime(10, ChronoUnit.DAYS, Clock.fixed(timeAfter12Days, ZoneId.of("UTC")));

        assertNull(userService.getUser(user.getId()));
        assertNotNull(userService.getUser(user2.getId()));


        Instant timeAfter2Month = Instant.now()
                                         .plus(2, ChronoUnit.MONTHS);
        userService.removeUsersByTime(1, ChronoUnit.MONTHS, Clock.fixed(timeAfter2Month, ZoneId.of("UTC")));
    }
}
