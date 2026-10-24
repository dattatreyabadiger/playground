package product_search.repository;

import product_search.model.Product;

import java.util.Set;

public interface ProductCatalogRepository {

    Product getProduct(int id);

    Set<Product> getProducts(String query);

    void addProduct(Product product);
}
