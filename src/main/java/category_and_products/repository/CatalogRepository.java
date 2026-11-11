package category_and_products.repository;

import category_and_products.model.CatalogNode;
import category_and_products.model.Product;

import java.util.List;
import java.util.Set;

public interface CatalogRepository {

    void addNode(int parentId, CatalogNode node);

    Set<Product> getProducts(int categoryId);

    List<List<String>> breadcrumb(int categoryId);
}
