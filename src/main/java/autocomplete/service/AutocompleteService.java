package autocomplete.service;

import autocomplete.repository.ProductRepository;

import java.util.List;

public class AutocompleteService {

    private final ProductRepository productRepository;

    public AutocompleteService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void add(String productName) {
        productRepository.insert(productName);
    }

    public boolean isPresent(String productName) {
        return productRepository.isPresent(productName);
    }

    public List<String> get(String keyword) {
        return productRepository.getSuggestions(keyword);
    }
}
