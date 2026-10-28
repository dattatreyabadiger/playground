package topk_products.repository;

import topk_products.model.Product;
import topk_products.model.ProductSales;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

public class InMemoryProductInventory implements ProductInventory {

    private final Map<Integer, Product> productMap;

    private final ConcurrentSkipListSet<ProductSales> productSalesSortedSet;
    private final Map<Integer, ProductSales> productSalesMap;

    public InMemoryProductInventory(Map<Integer, Product> productMap) {
        this.productMap = productMap;
        this.productSalesSortedSet = new ConcurrentSkipListSet<>(Comparator.comparing(ProductSales::getSalesCount)
                                                                           .reversed());
        this.productSalesMap = new HashMap<>();
    }

    @Override
    public Product getProduct(int productId) {
        return productMap.get(productId);
    }

    @Override
    public void incrementSalesCount(int productId) {
        ProductSales productSales = productSalesMap.get(productId);
        if (productSales == null) {
            productSales = new ProductSales(productId, 1);
            productSalesSortedSet.add(productSales);
            productSalesMap.put(productId, productSales);
        } else {
            productSalesSortedSet.remove(productSales);
            productSales.incrementSales();
            productSalesSortedSet.add(productSales);
        }
    }

    @Override
    public List<Product> getTopKProducts(int k) {
        return productSalesSortedSet.stream()
                                    .limit(k)
                                    .map(ProductSales::getId)
                                    .map(productMap::get)
                                    .collect(Collectors.toList());
    }


}
