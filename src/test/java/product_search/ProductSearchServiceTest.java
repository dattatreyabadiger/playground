package product_search;

import org.junit.jupiter.api.Test;
import product_search.model.Product;
import product_search.repository.InMemoryProductCatalogRepository;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductSearchServiceTest {

    private final IProductCatalogService productCatalogService;

    public ProductSearchServiceTest() {
        this.productCatalogService = new ProductCatalogService(new InMemoryProductCatalogRepository());
    }

    @Test
    public void ProductSearchTest() {
        productCatalogService.addProduct("Amul Milk", "this is delicious");
        productCatalogService.addProduct("Milk Milk", "this is delicious");
        productCatalogService.addProduct("Amul Chocolate Milk", "this is delicious");

        Set<Product> actualResult = productCatalogService.searchProducts("Amul");
        assertEquals(2, actualResult.size());

        Set<Product> actualResult2 = productCatalogService.searchProducts("Amul Milk");
        assertEquals(2, actualResult2.size());

        Set<Product> actualResult3 = productCatalogService.searchProducts("Amul Chocolate Milk");
        assertEquals(1, actualResult3.size());

        Set<Product> actualResult4 = productCatalogService.searchProducts("Chocolate Milk");
        assertEquals(1, actualResult4.size());

        Set<Product> actualResult5 = productCatalogService.searchProducts("Milk Milk");
        assertEquals(1, actualResult5.size());

        Set<Product> actualResult6 = productCatalogService.searchProducts("Milk");
        assertEquals(3, actualResult6.size());
    }
}
