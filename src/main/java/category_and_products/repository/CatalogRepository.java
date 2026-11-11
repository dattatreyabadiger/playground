package category_and_products.repository;

import category_and_products.model.CatalogNode;
import category_and_products.model.Product;

import java.util.Set;

public interface CatalogRepository {

    void addNode(int parentId, CatalogNode node);

    Set<Product> getProducts(int categoryId);

    String breadcrumb(int categoryId);
}
