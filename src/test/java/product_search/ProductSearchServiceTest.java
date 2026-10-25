package product_search;

import org.junit.jupiter.api.Test;
import product_search.model.Product;
import product_search.repository.InMemoryProductCatalogRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductSearchServiceTest {

    private final IProductCatalogService productCatalogService;

    public ProductSearchServiceTest() {
        this.productCatalogService = new ProductCatalogService(new InMemoryProductCatalogRepository());
    }

    @Test
    public void ProductSearchTest() {
        Product product = productCatalogService.addProduct("Amul Milk  ", "this is delicious");
        Product product2 = productCatalogService.addProduct("Milk Milk", "this is delicious");
        Product product3 = productCatalogService.addProduct("Amul   Chocolate Milk", "this is delicious");

        List<Product> actualResult = productCatalogService.searchProducts("Amul  ");
        assertEquals(List.of(product, product3), actualResult);

        List<Product> actualResult2 = productCatalogService.searchProducts("Amul  Milk");
        assertEquals(List.of(product, product3), actualResult2);

        List<Product> actualResult3 = productCatalogService.searchProducts("Amul  Chocolate Milk");
        assertEquals(List.of(product3), actualResult3);

        List<Product> actualResult4 = productCatalogService.searchProducts("Chocolate Milk");
        assertEquals(List.of(product3), actualResult4);

        List<Product> actualResult5 = productCatalogService.searchProducts("Milk Milk");
        assertEquals(List.of(product2), actualResult5);

        List<Product> actualResult6 = productCatalogService.searchProducts("Milk");
        assertEquals(List.of(product2, product, product3), actualResult6);

        List<Product> actualResult7 = productCatalogService.searchProducts(null);
        assertEquals(0, actualResult7.size());
    }
}
