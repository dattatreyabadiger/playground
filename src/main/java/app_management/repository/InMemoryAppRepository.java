package app_management.repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class InMemoryAppRepository implements AppRepository {

    private final Map<String, Set<String>> prefixToAppNamesMap;

    public InMemoryAppRepository() {
        this.prefixToAppNamesMap = new HashMap<>();
    }

    @Override
    public void insert(String appName) {
        Set<String> appKeywords = Arrays.stream(appName.split(" "))
                                        .map(String::toLowerCase)
                                        .collect(Collectors.toSet());

        String appNameLowerCase = appName.toLowerCase(Locale.ROOT);
        insert(appName, appNameLowerCase);
        for (String appKeyword : appKeywords) {
            insert(appName, appKeyword);
        }
    }

    private void insert(String appName, String appKeyword) {
        StringBuilder prefixBuilder = new StringBuilder();
        for (char c : appKeyword.toCharArray()) {
            prefixBuilder.append(c);
            prefixToAppNamesMap.computeIfAbsent(prefixBuilder.toString(), k -> new HashSet<>())
                               .add(appName);
        }
    }

    @Override
    public Set<String> findBy(String prefix) {
        Set<String> appNames = prefixToAppNamesMap.get(prefix.toLowerCase(Locale.ROOT));
        if (appNames == null)
            return Set.of();
        return appNames;
    }
}
