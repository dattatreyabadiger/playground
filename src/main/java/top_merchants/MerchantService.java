package top_merchants;

import top_merchants.model.Transaction;
import top_merchants.repository.InMemoryMerchantRepository;
import top_merchants.repository.MerchantRepository;

import java.util.List;

public class MerchantService {

    private final MerchantRepository merchantRepository;

    public MerchantService(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }

    public void addTransaction(Transaction transaction) {
        merchantRepository.addTransactionForMerchant(transaction.getMerchantId());
    }

    public List<Integer> getTopMerchants() {
        return merchantRepository.getTopMerchants();
    }
}
