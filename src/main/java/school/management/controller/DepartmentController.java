package school.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.management.entity.Departments;
import school.management.service.DepartmentService;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping(value = "/add-departments",consumes = "application/json",produces = "application/json")
    private ResponseEntity<Map<String,Object>> saveDepartments(@RequestBody Departments dep){
        try{
            Map<String ,Object> depa = departmentService.saveDepartment(dep);
            return ResponseEntity.ok(depa);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/get-departments")
    private ResponseEntity<Map<String,Object>> getDepartments(){
        try{
            Map<String ,Object> depa = departmentService.getAllDepartment();
            return ResponseEntity.ok(depa);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
