package school.management.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.management.entity.Departments;
import school.management.repository.DepartmentRepo;
import school.management.service.DepartmentService;

import java.util.Map;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Override
    public Map<String, Object> saveDepartment(Departments dep) {
        if(departmentRepo.existsByCodeAndValue(dep.getCode(),dep.getValue())){
            return Map.of("message","code and value already exists","status",false);
        }
        departmentRepo.save(dep);
        return Map.of("message","Department added successfully!","status",true);
    }

    @Override
    public Map<String, Object> getAllDepartment() {
        return Map.of("message","Departments fetched successfully!","data",departmentRepo.findAll(),"status",true);
    }
}
