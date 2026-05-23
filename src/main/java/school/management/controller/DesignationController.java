package school.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.management.entity.DesignationEntity;
import school.management.service.DesignationService;

@RestController
@RequestMapping("/api")
public class DesignationController {

    @Autowired
    private DesignationService designationService;


    @PostMapping(value = "/add-designation",consumes = "application/json",produces = "application/json")
    private ResponseEntity<?> saveDesignationData(@RequestBody DesignationEntity designation){
        try{
            String status = designationService.saveDesignation(designation);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
