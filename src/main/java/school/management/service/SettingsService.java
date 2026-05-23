package school.management.service;

import school.management.entity.Settings;

import java.util.Map;

public interface SettingsService {

    String saveSettings(Settings set);

    Map<String,Object> getAllSettings();
}
