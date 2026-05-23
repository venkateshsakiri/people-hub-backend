package school.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.management.entity.AccessRightsEntity;
import school.management.service.AccessRightsService;

@RestController
@RequestMapping("/api")
public class AccessRightsController {

    @Autowired
    private AccessRightsService accessRightsService;


    @PostMapping(value = "/add-access-rights",consumes = "application/json",produces = "application/json")
    private ResponseEntity<?> saveAccessRights(@RequestBody AccessRightsEntity accessRights){
        try{
            String status = accessRightsService.saveAccessRights(accessRights);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
