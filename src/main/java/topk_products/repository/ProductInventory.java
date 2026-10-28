package topk_products.repository;

import topk_products.model.Product;

import java.util.List;

public interface ProductInventory {
    Product getProduct(int productId);

    void incrementSalesCount(int productId);

    List<Product> getTopKProducts(int k);
}
