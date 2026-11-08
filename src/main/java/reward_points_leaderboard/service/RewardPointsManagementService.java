package reward_points_leaderboard.service;

import reward_points_leaderboard.model.ImmutableCustomerInfo;
import reward_points_leaderboard.repository.RewardPointsRepository;

import java.math.BigDecimal;
import java.util.List;

public class RewardPointsManagementService {

    private final RewardPointsRepository rewardPointsRepository;

    public RewardPointsManagementService(RewardPointsRepository rewardPointsRepository) {
        this.rewardPointsRepository = rewardPointsRepository;
    }

    public void addRewardPoints(int customerId, BigDecimal orderAmount) {
        int rewardPoints = orderAmount.divide(BigDecimal.valueOf(100))
                                      .intValue();
        rewardPointsRepository.addRewardPoints(customerId, rewardPoints);
    }

    public List<ImmutableCustomerInfo> getTopCustomers(int customersCount) {
        return rewardPointsRepository.getTopCustomers(customersCount);
    }

    public List<ImmutableCustomerInfo> getCustomersRank(int customerId) {
        return rewardPointsRepository.getCustomersRank(customerId);
    }
}
