package reward_points_leaderboard.repository;

import reward_points_leaderboard.model.ImmutableCustomerInfo;

import java.util.List;

public interface RewardPointsRepository {
    void addRewardPoints(int customerId, int rewardPoints);

    List<ImmutableCustomerInfo> getCustomersRank(int customerId);

    List<ImmutableCustomerInfo> getTopCustomers(int customersCount);
}
