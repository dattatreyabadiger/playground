package category_and_products;


import category_and_products.model.CatalogNode;
import category_and_products.model.Category;
import category_and_products.model.Product;
import category_and_products.repository.InMemoryCatalogRepository;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CatalogServiceTest {

    private final CatalogService catalogService;

    public CatalogServiceTest() {
        this.catalogService = new CatalogService(new InMemoryCatalogRepository());
    }

    @Test
    public void test() {
        catalogService.addNode(1, new Category(1, "category1"));
        catalogService.addNode(1, new Category(2, "subCat1"));

        Set<Product> actualProducts = catalogService.getProducts(2);
        assertEquals(0, actualProducts.size());

        catalogService.addNode(2, new Product(3, "prod1"));
        catalogService.addNode(2, new Product(4, "prod2"));
        assertThrows(IllegalArgumentException.class,
                     () -> catalogService.addNode(4, new Product(5, "prod3")));
        assertThrows(IllegalArgumentException.class,
                     () -> catalogService.addNode(1, new Product(3, "prod3")));

        Set<Product> actualProducts2 = catalogService.getProducts(1);
        Set<String> actualProductNames = actualProducts2.stream()
                                                        .map(CatalogNode::getName)
                                                        .collect(Collectors.toSet());
        assertEquals(Set.of("prod1", "prod2"), actualProductNames);

        String path = catalogService.breadcrumb(2);
        assertEquals("category1 -> subCat1", path);

        String path2 = catalogService.breadcrumb(4);
        assertEquals("category1 -> subCat1 -> prod2", path2);

        assertThrows(IllegalArgumentException.class,
                     () -> catalogService.addNode(1, new Product(6, "prod6")));
        assertThrows(IllegalArgumentException.class,
                     () -> catalogService.breadcrumb(6));
    }
}
