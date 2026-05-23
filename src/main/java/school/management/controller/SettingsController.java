package school.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.management.entity.Settings;
import school.management.service.SettingsService;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    @PostMapping(value = "/add-settings",consumes = "application/json",produces = "application/json")
    private ResponseEntity<?> saveSettings(@RequestBody Settings settings){
        System.out.println(settings);
        try{
            String status = settingsService.saveSettings(settings);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/get-settings")
    private ResponseEntity<Map<String,Object>> getAllSettings(){
        try{
            Map<String,Object> setting = settingsService.getAllSettings();
            return ResponseEntity.ok(setting);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    @PutMapping(value = "/update-settings")
//    private ResponseEntity<Map<String,Object>> getAllSettings(){
//        try{
//            Map<String,Object> setting = settingsService.getAllSettings();
//            return ResponseEntity.ok(setting);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }


}
