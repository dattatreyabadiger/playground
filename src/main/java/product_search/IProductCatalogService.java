package product_search;

import product_search.model.Product;

import java.util.Set;

public interface IProductCatalogService {


    Product addProduct(String name, String details);

    Set<Product> searchProducts(String query);
}
