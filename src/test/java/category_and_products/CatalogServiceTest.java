package category_and_products;


import category_and_products.model.CatalogNode;
import category_and_products.model.Category;
import category_and_products.model.Product;
import category_and_products.repository.InMemoryCatalogRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CatalogServiceTest {

    private final CatalogService catalogService;

    public CatalogServiceTest() {
        this.catalogService = new CatalogService(new InMemoryCatalogRepository());
    }

    @Test
    public void test() {
        catalogService.addProducts(1, new Category(1, "category1"));
        catalogService.addProducts(1, new Category(2, "subCat1"));

        Set<Product> actualProducts = catalogService.getProducts(2);
        assertEquals(0, actualProducts.size());

        catalogService.addProducts(2, new Product(3, "prod1"));
        catalogService.addProducts(2, new Product(4, "prod2"));
        Set<Product> actualProducts2 = catalogService.getProducts(2);
        Set<String> actualProductNames = actualProducts2.stream()
                                             .map(CatalogNode::getName)
                                             .collect(Collectors.toSet());
        assertEquals(Set.of("prod1", "prod2"), actualProductNames);

        List<List<String>> actualPaths = catalogService.breadcrumb(2);
        assertFalse(false);
    }
}
