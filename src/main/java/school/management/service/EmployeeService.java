package school.management.service;


import school.management.entity.EmployeeRegister;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeeService {

    Map<String, Object> saveEmployee(EmployeeRegister employee);

    List<EmployeeRegister> findByAllRolecodeIn(List<String> roles);

    Map<String, Object> selfOnboardingEmployee(Map<String, Object> request);

    Optional<EmployeeRegister> employeeFindById(String id);



}
