package school.management.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.management.entity.Settings;
import school.management.repository.SettingsRepo;
import school.management.service.SettingsService;

import java.util.Map;

@Service
public class SettingsServiceImpl implements SettingsService {

    @Autowired
    private SettingsRepo settingsRepo;

    @Override
    public String saveSettings(Settings sett) {
        settingsRepo.save(sett);
        return "settings saved successfully!";
    }

    @Override
    public Map<String, Object> getAllSettings() {
        return Map.of("message","Fetched all settings","data",settingsRepo.findAll().get(0),"status",true);
    }
}
