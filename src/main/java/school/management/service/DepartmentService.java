package school.management.service;

import school.management.entity.Departments;

import java.util.Map;

public interface DepartmentService {

    Map<String ,Object> saveDepartment(Departments dep);

    Map<String ,Object> getAllDepartment();

}
