package app_management.service;

import app_management.repository.AppRepository;

import java.util.Set;

public class AppManagementService {

    private final AppRepository appRepository;

    public AppManagementService(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public void addApplication(String appName) {
        appRepository.insert(appName);
    }

    public Set<String> searchApps(String prefix) {
        if (prefix == null || prefix.isBlank())
            return Set.of();
        return appRepository.findBy(prefix);
    }
}
