package app_management;

import app_management.repository.InMemoryAppRepository;
import app_management.service.AppManagementService;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppManagementServiceTest {

    private final AppManagementService appManagementService;


    public AppManagementServiceTest() {
        this.appManagementService = new AppManagementService(new InMemoryAppRepository());

        appManagementService.addApplication("Facebook");
        appManagementService.addApplication("HDFC Bank");
        appManagementService.addApplication("ICICI Bank");
        appManagementService.addApplication("phonepe");
    }

    @Test
    public void searchExistingApplication() {
        Set<String> actualAppSuggestions = appManagementService.searchApps("face");
        assertEquals(Set.of("Facebook"), actualAppSuggestions);
    }

    @Test
    public void searchExistingApplicationWithMultipleWords() {
        Set<String> actualAppSuggestions = appManagementService.searchApps("HDFC B");
        assertEquals(Set.of("HDFC Bank"), actualAppSuggestions);
    }

    @Test
    public void searchExistingApplication_multipleResults() {
        Set<String> actualAppSuggestions = appManagementService.searchApps("ba");
        assertEquals(Set.of("HDFC Bank", "ICICI Bank"), actualAppSuggestions);
    }

    @Test
    public void searchInExistingApplication_returnsEmptySet() {
        assertEquals(Set.of(), appManagementService.searchApps("BN"));
        assertEquals(Set.of(), appManagementService.searchApps("book"));
    }

    @Test
    public void searchByEmptyPrefix_returnsEmptySet() {
        assertEquals(Set.of(), appManagementService.searchApps(""));
    }

    @Test
    public void searchByNullPrefix_returnsEmptySet() {
        assertEquals(Set.of(), appManagementService.searchApps(null));
    }

    @Test
    public void searchByWhitespacePrefix_returnsEmptySet() {
        assertEquals(Set.of(), appManagementService.searchApps(" "));
    }
}
