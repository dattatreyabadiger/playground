package product_search;

import product_search.model.Product;

import java.util.List;
import java.util.Set;

public interface IProductCatalogService {


    Product addProduct(String name, String details);

    List<Product> searchProducts(String query);
}
