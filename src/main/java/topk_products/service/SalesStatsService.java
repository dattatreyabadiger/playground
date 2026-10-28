package topk_products.service;

import topk_products.model.Product;
import topk_products.repository.ProductInventory;

import java.util.List;

public class SalesStatsService implements ISalesStatsService {

    private final ProductInventory productInventory;

    public SalesStatsService(ProductInventory productInventory) {
        this.productInventory = productInventory;
    }

    @Override
    public List<Product> getTopSellingProducts(int productCount) {
        return productInventory.getTopKProducts(productCount);
    }

    @Override
    public void incrementSales(int productId) {
        productInventory.incrementSalesCount(productId);
    }
}
