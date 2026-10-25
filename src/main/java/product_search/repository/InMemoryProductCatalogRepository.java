package product_search.repository;

import product_search.model.Product;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class InMemoryProductCatalogRepository implements ProductCatalogRepository {

    private final Map<Integer, Product> productMap;
    private final Map<String, Map<Integer, Integer>> keywordsToProductIdsMap;

    public InMemoryProductCatalogRepository() {
        this.productMap = new HashMap<>();
        this.keywordsToProductIdsMap = new HashMap<>();
    }

    @Override
    public synchronized Product getProduct(int id) {
        return productMap.get(id);
    }

    @Override
    public synchronized List<Product> getProducts(String query) {
        Map<String, Integer> keywords = getKeywords(query);

        Set<Integer> filteredProductIds = new HashSet<>();
        boolean fillFilteredProductIds = true;
        for (var keyword : keywords.entrySet()) {
            Map<Integer, Integer> productIdToKeywordCount = keywordsToProductIdsMap.get(keyword.getKey());
            if (productIdToKeywordCount == null)
                return List.of();

            Set<Integer> matchingIds
                    = productIdToKeywordCount.entrySet()
                                             .stream()
                                             .filter(a -> a.getValue() >= keyword.getValue())
                                             .map(Map.Entry::getKey)
                                             .collect(Collectors.toSet());

            if (fillFilteredProductIds) {
                filteredProductIds = matchingIds;
                fillFilteredProductIds = false;
            }

            filteredProductIds.retainAll(matchingIds);
        }
        Comparator<Product> comparator = Comparator.comparing((Product product) -> getScore(query, product.getName()))
                                                   .reversed()
                                                   .thenComparing(Product::getName)
                                                   .thenComparing(Product::getId);
        return filteredProductIds.stream()
                                 .map(this::getProduct)
                                 .sorted(comparator)
                                 .collect(Collectors.toList());
    }

    @Override
    public synchronized void addProduct(Product product) {
        Map<String, Integer> keywords = getKeywords(product.getName());
        for (var keyword : keywords.entrySet()) {
            keywordsToProductIdsMap.computeIfAbsent(keyword.getKey(), k -> new HashMap<>())
                                   .put(product.getId(), keyword.getValue());
        }
        productMap.put(product.getId(), product);
    }

    private int getScore(String query, String productName) {
        int queryLen = query.replaceAll(" +", " ")
                            .length();
        int productNameLen = productName.replaceAll(" +", " ")
                                        .length();
        return 100 * queryLen / productNameLen;
    }

    private static Map<String, Integer> getKeywords(String query) {
        List<String> keywordList = Arrays.stream(query.split(" +"))
                                         .map(str -> str.toLowerCase(Locale.ROOT))
                                         .toList();
        Map<String, Integer> keywordFreqMap = new HashMap<>();
        for (String keyword : keywordList) {
            keywordFreqMap.put(keyword, keywordFreqMap.getOrDefault(keyword, 0) + 1);
        }

        return keywordFreqMap;
    }
}