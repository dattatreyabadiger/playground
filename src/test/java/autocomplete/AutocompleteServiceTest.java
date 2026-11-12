package autocomplete;

import autocomplete.repository.InMemoryProductRepository;
import autocomplete.service.AutocompleteService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AutocompleteServiceTest {

    private final AutocompleteService autocompleteService;

    public AutocompleteServiceTest() {
        autocompleteService = new AutocompleteService(new InMemoryProductRepository());
    }

    @Test
    public void test() {
        autocompleteService.add("amul");
        autocompleteService.add("amul choco");
        autocompleteService.add("chocolate");
        autocompleteService.add("choco");

        assertFalse(autocompleteService.isPresent("amu"));
        assertFalse(autocompleteService.isPresent("Amul"));
        assertFalse(autocompleteService.isPresent("amulchoco"));
        assertTrue(autocompleteService.isPresent("amul"));
        assertTrue(autocompleteService.isPresent("amul choco"));

        List<String> actualSuggestions = autocompleteService.get("ch");
        assertEquals(List.of("choco", "chocolate"), actualSuggestions);

        List<String> actualSuggestions2 = autocompleteService.get("am");
        assertEquals(List.of("amul", "amul choco"), actualSuggestions2);
    }
}
