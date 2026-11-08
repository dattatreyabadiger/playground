package reward_points_leaderboard.repository;

import reward_points_leaderboard.model.CustomerInfo;
import reward_points_leaderboard.model.ImmutableCustomerInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryRewardPointsRepository implements RewardPointsRepository {

    private final TreeSet<CustomerInfo> rewardPointsStore;
    private final Map<Integer, CustomerInfo> customerIdToInfoMap;

    public InMemoryRewardPointsRepository() {
        Comparator<CustomerInfo> customerInfoComparator = Comparator.comparing(CustomerInfo::getRewardPoints)
                                                                    .reversed()
                                                                    .thenComparing(CustomerInfo::getId);
        this.rewardPointsStore = new TreeSet<>(customerInfoComparator);
        customerIdToInfoMap = new HashMap<>();
    }

    @Override
    public synchronized void addRewardPoints(int customerId, int rewardPoints) {
        CustomerInfo existingCustomerInfo = customerIdToInfoMap.get(customerId);
        if (existingCustomerInfo == null) {
            CustomerInfo newCustomerInfo = new CustomerInfo(customerId, rewardPoints);
            customerIdToInfoMap.put(customerId, newCustomerInfo);
            rewardPointsStore.add(newCustomerInfo);
        } else {
            rewardPointsStore.remove(existingCustomerInfo);
            int totalRewardPoints = existingCustomerInfo.getRewardPoints() + rewardPoints;
            existingCustomerInfo.setRewardPoints(totalRewardPoints);
            rewardPointsStore.add(existingCustomerInfo);
        }
    }

    @Override
    public synchronized List<ImmutableCustomerInfo> getCustomersRank(int customerId) {
        CustomerInfo currentCustomerInfo = customerIdToInfoMap.get(customerId);
        if (currentCustomerInfo == null)
            throw new IllegalArgumentException("Unknown customer ID");

        int currentCustomerRank = rewardPointsStore.headSet(currentCustomerInfo)
                                                   .size() + 1;
        // lower mean left of tree -> higher score
        CustomerInfo aboveCustomerInfo = rewardPointsStore.lower(currentCustomerInfo);
        // higher mean right of tree -> less score (as tree is keeping in reverse order)
        CustomerInfo lowerCustomerInfo = rewardPointsStore.higher(currentCustomerInfo);

        List<ImmutableCustomerInfo> res = new ArrayList<>();
        if (aboveCustomerInfo != null) {
            CustomerInfo aboveCustomerInfo2 = rewardPointsStore.lower(aboveCustomerInfo);
            addToRes(res, aboveCustomerInfo2, currentCustomerRank - 2);
            addToRes(res, aboveCustomerInfo, currentCustomerRank - 1);
        }

        addToRes(res, currentCustomerInfo, currentCustomerRank);

        if (lowerCustomerInfo != null) {
            CustomerInfo lowerCustomerInfo2 = rewardPointsStore.higher(lowerCustomerInfo);
            addToRes(res, lowerCustomerInfo, currentCustomerRank + 1);
            addToRes(res, lowerCustomerInfo2, currentCustomerRank + 2);
        }
        return res;
    }

    @Override
    public synchronized List<ImmutableCustomerInfo> getTopCustomers(int customersCount) {
        AtomicInteger rank = new AtomicInteger(1);
        return rewardPointsStore.stream()
                                .limit(customersCount)
                                .map(customerInfo -> createImmutableCustomerInfo(customerInfo, rank.getAndIncrement()))
                                .toList();
    }

    private void addToRes(List<ImmutableCustomerInfo> res, CustomerInfo customerInfo, int rank) {
        if (customerInfo == null)
            return;

        res.add(createImmutableCustomerInfo(customerInfo, rank));
    }

    private ImmutableCustomerInfo createImmutableCustomerInfo(CustomerInfo customerInfo, int rank) {
        return new ImmutableCustomerInfo(customerInfo.getId(), rank,
                                         customerInfo.getRewardPoints());
    }

}
