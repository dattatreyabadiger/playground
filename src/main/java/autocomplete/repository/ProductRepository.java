package autocomplete.repository;

import java.util.List;

public interface ProductRepository {

    void insert(String productName);

    boolean isPresent(String productName);

    List<String> getSuggestions(String keyword);
}
