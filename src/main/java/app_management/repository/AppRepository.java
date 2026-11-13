package app_management.repository;

import java.util.Set;

public interface AppRepository {

    void insert(String appName);

    Set<String> findBy(String prefix);
}
