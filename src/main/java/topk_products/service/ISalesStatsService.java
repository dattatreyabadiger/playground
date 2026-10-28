package topk_products.service;

import topk_products.model.Product;

import java.util.List;

public interface ISalesStatsService {

    List<Product> getTopSellingProducts(int productCount);

    void incrementSales(int productId);
}
