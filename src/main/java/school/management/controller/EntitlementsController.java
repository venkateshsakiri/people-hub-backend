package school.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.management.entity.EntitlementsEntity;
import school.management.service.EntitlementsService;

@RestController
@RequestMapping("/api")
public class EntitlementsController {

    @Autowired
    private EntitlementsService entitlementsService;

    @PostMapping(value = "/add-entitlements",consumes = "application/json",produces = "application/json")
    private ResponseEntity<?> addEntitlements(@RequestBody EntitlementsEntity entitlements){
        try{
            String status = entitlementsService.saveEntitlements(entitlements);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
