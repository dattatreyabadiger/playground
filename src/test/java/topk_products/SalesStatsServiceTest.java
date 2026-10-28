package topk_products;

import org.junit.jupiter.api.Test;
import topk_products.model.Product;
import topk_products.repository.InMemoryProductInventory;
import topk_products.service.SalesStatsService;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SalesStatsServiceTest {

    private final SalesStatsService salesStatsService;

    private final Product product1 = new Product("abc", 10);
    private final Product product2 = new Product("def", 9);
    private final Product product3 = new Product("fgh", 11);
    private final Product product4 = new Product("gef", 12);

    public SalesStatsServiceTest() {

        Map<Integer, Product> productMap = Map.of(product1.getId(), product1,
                                                  product2.getId(), product2,
                                                  product3.getId(), product3,
                                                  product4.getId(), product4);

        this.salesStatsService = new SalesStatsService(new InMemoryProductInventory(productMap));
    }

    @Test
    public void getTopSellingProducts() {
        salesStatsService.incrementSales(product1.getId());
        salesStatsService.incrementSales(product1.getId());
        salesStatsService.incrementSales(product1.getId());
        salesStatsService.incrementSales(product1.getId());
        salesStatsService.incrementSales(product1.getId());
        salesStatsService.incrementSales(product1.getId());

        salesStatsService.incrementSales(product2.getId());
        salesStatsService.incrementSales(product2.getId());

        salesStatsService.incrementSales(product3.getId());
        salesStatsService.incrementSales(product3.getId());
        salesStatsService.incrementSales(product3.getId());

        salesStatsService.incrementSales(product4.getId());

        List<Product> actualTopSellingProducts = salesStatsService.getTopSellingProducts(2);
        assertEquals(List.of(product1, product3), actualTopSellingProducts);
    }

}
