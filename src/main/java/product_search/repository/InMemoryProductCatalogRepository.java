package product_search.repository;

import product_search.model.Keyword;
import product_search.model.Product;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class InMemoryProductCatalogRepository implements ProductCatalogRepository {

    private final Map<Integer, Product> productMap;
    private final Map<Keyword, Set<Integer>> keywordsToProductIdsMap;

    public InMemoryProductCatalogRepository() {
        this.productMap = new HashMap<>();
        this.keywordsToProductIdsMap = new HashMap<>();
    }

    @Override
    public Product getProduct(int id) {
        return productMap.get(id);
    }

    @Override
    public Set<Product> getProducts(String query) {
        Set<Keyword> keywords = getKeywords(query);

        Set<Integer> filteredProductIds = new HashSet<>();
        boolean fillFilteredProductIds = true;
        for (Keyword keyword : keywords) {
            Set<Integer> productIds = keywordsToProductIdsMap.get(keyword);
            if (productIds == null)
                return Set.of();
            if (fillFilteredProductIds) {
                filteredProductIds = productIds;
                fillFilteredProductIds = false;
            }

            filteredProductIds.retainAll(productIds);
        }
        return filteredProductIds.stream()
                                 .map(this::getProduct)
                                 .collect(Collectors.toSet());
    }

    @Override
    public void addProduct(Product product) {
        Set<Keyword> keywords = getKeywords(product.getName());
        for (Keyword keyword : keywords) {
            keywordsToProductIdsMap.computeIfAbsent(keyword, k -> new HashSet<>())
                                   .add(product.getId());
        }
        productMap.put(product.getId(), product);
    }

    private static Set<Keyword> getKeywords(String query) {
        List<String> keywordList = Arrays.stream(query.split(" "))
                                         .map(String::toLowerCase)
                                         .toList();
        Map<String, Integer> keywordFreqMap = new HashMap<>();
        for (String keyword : keywordList) {
            keywordFreqMap.put(keyword, keywordFreqMap.getOrDefault(keyword, 0)+1);
        }

        return keywordFreqMap.entrySet()
                             .stream()
                             .map(entry -> new Keyword(entry.getKey(), entry.getValue()))
                             .collect(Collectors.toSet());
    }
}