package product_search;

import product_search.model.Product;
import product_search.repository.ProductCatalogRepository;


import java.util.List;
import java.util.Set;

public class ProductCatalogService implements IProductCatalogService {

    private final ProductCatalogRepository productCatalogRepository;

    public ProductCatalogService(ProductCatalogRepository productCatalogRepository) {
        this.productCatalogRepository = productCatalogRepository;
    }

    @Override
    public Product addProduct(String name, String details) {
        Product product = new Product(name, details);
        productCatalogRepository.addProduct(product);
        return product;
    }

    @Override
    public List<Product> searchProducts(String query) {
        if (query == null)
            return List.of();
        return productCatalogRepository.getProducts(query);
    }
}
