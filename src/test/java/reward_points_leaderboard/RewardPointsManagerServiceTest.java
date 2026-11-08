package reward_points_leaderboard;

import org.junit.jupiter.api.Test;
import reward_points_leaderboard.model.ImmutableCustomerInfo;
import reward_points_leaderboard.repository.InMemoryRewardPointsRepository;
import reward_points_leaderboard.service.RewardPointsManagementService;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RewardPointsManagerServiceTest {

    private final RewardPointsManagementService rewardPointsManagementService;

    public RewardPointsManagerServiceTest() {
        this.rewardPointsManagementService = new RewardPointsManagementService(new InMemoryRewardPointsRepository());
    }

    @Test
    public void getTopCustomersTest() {
        rewardPointsManagementService.addRewardPoints(1, BigDecimal.valueOf(1000));
        rewardPointsManagementService.addRewardPoints(2, BigDecimal.valueOf(7000));
        rewardPointsManagementService.addRewardPoints(3, BigDecimal.valueOf(8000));
        rewardPointsManagementService.addRewardPoints(1, BigDecimal.valueOf(5500));

        List<ImmutableCustomerInfo> actualTopCustomers = rewardPointsManagementService.getTopCustomers(2);
        List<ImmutableCustomerInfo> expectedTopCustomers = List.of(new ImmutableCustomerInfo(3, 1, 80),
                                                                   new ImmutableCustomerInfo(2, 2, 70));
        assertEquals(expectedTopCustomers, actualTopCustomers);

        // attempt 2 with updates
        rewardPointsManagementService.addRewardPoints(1, BigDecimal.valueOf(2500));
        List<ImmutableCustomerInfo> actualTopCustomers2 = rewardPointsManagementService.getTopCustomers(2);
        List<ImmutableCustomerInfo> expectedTopCustomers2 = List.of(new ImmutableCustomerInfo(1, 1, 90),
                                                                    new ImmutableCustomerInfo(3, 2, 80));
        assertEquals(expectedTopCustomers2, actualTopCustomers2);
    }

    @Test
    public void getCustomersRankTest() {
        rewardPointsManagementService.addRewardPoints(1, BigDecimal.valueOf(1000));
        rewardPointsManagementService.addRewardPoints(2, BigDecimal.valueOf(7000));
        rewardPointsManagementService.addRewardPoints(3, BigDecimal.valueOf(8000));
        rewardPointsManagementService.addRewardPoints(1, BigDecimal.valueOf(5500));

        rewardPointsManagementService.addRewardPoints(1, BigDecimal.valueOf(2500));

        // checking rank after adding few more entries
        rewardPointsManagementService.addRewardPoints(5, BigDecimal.valueOf(5500));
        rewardPointsManagementService.addRewardPoints(4, BigDecimal.valueOf(1500));
        rewardPointsManagementService.addRewardPoints(6, BigDecimal.valueOf(2500));

        // 1(90), 2(70), 3(80), 4(15), 5(55), 6(25)
        // rank -> 1, 3, 2, 5, 6, 4

        List<ImmutableCustomerInfo> neighbouringCustomersForCurrCustomer =
                rewardPointsManagementService.getCustomersRank(1);
        List<ImmutableCustomerInfo> expectedCustomerInfoList = List.of(new ImmutableCustomerInfo(1, 1, 90),
                                                                       new ImmutableCustomerInfo(3, 2, 80),
                                                                       new ImmutableCustomerInfo(2, 3, 70));
        assertEquals(expectedCustomerInfoList, neighbouringCustomersForCurrCustomer);

        List<ImmutableCustomerInfo> neighbouringCustomersForCurrCustomer2 =
                rewardPointsManagementService.getCustomersRank(2);
        List<ImmutableCustomerInfo> expectedCustomerInfoList2 = List.of(new ImmutableCustomerInfo(1, 1, 90),
                                                                        new ImmutableCustomerInfo(3, 2, 80),
                                                                        new ImmutableCustomerInfo(2, 3, 70),
                                                                        new ImmutableCustomerInfo(5, 4, 55),
                                                                        new ImmutableCustomerInfo(6, 5, 25));
        assertEquals(expectedCustomerInfoList2, neighbouringCustomersForCurrCustomer2);
    }
}
