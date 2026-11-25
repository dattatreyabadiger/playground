package top_merchants;

import org.junit.jupiter.api.Test;
import top_merchants.model.Transaction;
import top_merchants.repository.InMemoryMerchantRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MerchantServiceTest {

    private final MerchantService merchantService;

    public MerchantServiceTest() {
        this.merchantService = new MerchantService(new InMemoryMerchantRepository());
    }

    @Test
    public void testMerchantService() {
        merchantService.addTransaction(new Transaction(111, 2));
        merchantService.addTransaction(new Transaction(113, 3));
        merchantService.addTransaction(new Transaction(114, 1));
        merchantService.addTransaction(new Transaction(112, 2));

        List<Integer> topMerchants = merchantService.getTopMerchants();
        assertEquals(List.of(2, 1, 3), topMerchants);
    }

    @Test
    public void testNoTransactions() {
        assertEquals(List.of(), merchantService.getTopMerchants());
    }

}
