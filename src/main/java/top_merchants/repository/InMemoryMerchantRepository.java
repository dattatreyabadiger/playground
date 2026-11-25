package top_merchants.repository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryMerchantRepository implements MerchantRepository {

    private final Map<Integer, Integer> merchantIdToSalesMap;

    public InMemoryMerchantRepository() {
        this.merchantIdToSalesMap = new HashMap<>();
    }

    public void addTransactionForMerchant(int merchantId) {
        merchantIdToSalesMap.put(merchantId, merchantIdToSalesMap.getOrDefault(merchantId, 0) + 1);
    }

    public List<Integer> getTopMerchants() {
        Comparator<Map.Entry<Integer, Integer>> comparator = Map.Entry.<Integer, Integer>comparingByValue()
                                                                      .reversed()
                                                                      .thenComparing(Map.Entry::getKey);
        return merchantIdToSalesMap.entrySet()
                                   .stream()
//                                   .sorted(comparator)
                                   .sorted((a, b) -> {
                                       int bySales = Integer.compare(b.getValue(), a.getValue());
                                       if (bySales != 0)
                                           return bySales;
                                       return Integer.compare(a.getKey(), b.getKey());
                                   })
                                   .map(Map.Entry::getKey)
                                   .toList();
    }
}
