package top_merchants.repository;

import java.util.List;

public interface MerchantRepository {

    void addTransactionForMerchant(int merchantId);

    List<Integer> getTopMerchants();
}
