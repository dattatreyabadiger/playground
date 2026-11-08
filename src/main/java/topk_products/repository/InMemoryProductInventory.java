package topk_products.repository;

import topk_products.model.Product;
import topk_products.model.ProductSales;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class InMemoryProductInventory implements ProductInventory {

    private final Map<Integer, Product> productMap;

    //    private final ConcurrentSkipListSet<ProductSales> productSalesSortedSet;
    private final PriorityQueue<ProductSales> productSalesSortedQueue;
    private final Map<Integer, ProductSales> productSalesMap;

    public InMemoryProductInventory(Map<Integer, Product> productMap) {
        this.productMap = productMap;
//        this.productSalesSortedSet = new ConcurrentSkipListSet<>(Comparator.comparing(ProductSales::getSalesCount)
//                                                                           .reversed());
        this.productSalesSortedQueue = new PriorityQueue<>(Comparator.comparing(ProductSales::getSalesCount)
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
            productSalesSortedQueue.add(productSales);
//            productSalesSortedSet.add(productSales);
            productSalesMap.put(productId, productSales);
        } else {
//            productSalesSortedSet.remove(productSales);
            productSalesSortedQueue.remove(productSales);
            productSales.incrementSales();
            productSalesSortedQueue.add(productSales);
//            productSalesSortedSet.add(productSales);
        }
    }

    @Override
    public List<Product> getTopKProducts(int k) {
        List<Product> products = new ArrayList<>();
        List<ProductSales> poppedProductSales = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            ProductSales productSales = productSalesSortedQueue.poll();
            poppedProductSales.add(productSales);
            products.add(productMap.get(productSales.getId()));
        }

        productSalesSortedQueue.addAll(poppedProductSales);
        return products;
//        return productSalesSortedSet.stream()
//                                    .limit(k)
//                                    .map(ProductSales::getId)
//                                    .map(productMap::get)
//                                    .collect(Collectors.toList());
    }


}
