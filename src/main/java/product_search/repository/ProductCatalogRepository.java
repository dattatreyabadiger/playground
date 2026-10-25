package product_search.repository;

import product_search.model.Product;

import java.util.List;
import java.util.Set;

public interface ProductCatalogRepository {

    Product getProduct(int id);

    List<Product> getProducts(String query);

    void addProduct(Product product);
}
