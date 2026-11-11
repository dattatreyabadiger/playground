package category_and_products;

import category_and_products.model.CatalogNode;
import category_and_products.model.Product;
import category_and_products.repository.CatalogRepository;

import java.util.List;
import java.util.Set;


public class CatalogService {

    private final CatalogRepository catalogRepository;

    public CatalogService(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    public void addProducts(int parentId, CatalogNode node) {
        catalogRepository.addNode(parentId, node);
    }

    public Set<Product> getProducts(int categoryId) {
        return catalogRepository.getProducts(categoryId);
    }

    public List<List<String>> breadcrumb(int categoryId) {
        return catalogRepository.breadcrumb(categoryId);
    }
}
