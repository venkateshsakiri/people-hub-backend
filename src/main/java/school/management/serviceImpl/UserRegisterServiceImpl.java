package school.management.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.management.config.SecurityConfig;
import school.management.entity.EmployeeRegister;
import school.management.entity.UserRegister;
import school.management.repository.EmployeeRepo;
import school.management.repository.UserRegisterRepo;
import school.management.service.EmployeeService;
import school.management.service.UserRegisterService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    @Autowired
    public UserRegisterRepo userRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private SecurityConfig passwordEncoder;

    @Autowired
    public EmployeeService employeeService;

    @Override
    public Map<String, Object> saveUser(UserRegister user) {
        String encryptPassword = passwordEncoder.passwordEncoder().encode(user.getPassword());
        user.setPassword(encryptPassword);
        userRepo.save(user);
        EmployeeRegister emp = new EmployeeRegister();
        String lastId = employeeRepo.findLastEmployeeId();
        String newId = generateEmployeeId(lastId);
        emp.setId(user.getId());
        emp.setRole(user.getRole());
        emp.setRoleCode(user.getRoleCode());
        emp.setFirstName(user.getFirstName());
        emp.setLastName(user.getLastName());
        emp.setEmail(user.getEmail());
        emp.setEmpId(newId);
        employeeService.saveEmployee(emp);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Employee Registered successfully!");
        response.put("id", user.getId());
        response.put("empId",emp.getEmpId());
        return response;
    }

    @Override
    public Optional<UserRegister> existUser(String email) {
        Optional<UserRegister> user = userRepo.findByEmail(email);
        if(user.isPresent()){
            return user;
        }
        return Optional.empty();
    }

    @Override
    public String directUserRegister(UserRegister user) {
        String encryptPassword = passwordEncoder.passwordEncoder().encode(user.getPassword());
        user.setPassword(encryptPassword);
        userRepo.save(user);
        return "User registered successfully!";
    }

    public String generateEmployeeId(String lastId) {

        String prefix = "HFTCOP";
        int nextNumber = 1;

        if (lastId != null && lastId.startsWith(prefix)) {
            String numberPart = lastId.substring(prefix.length());
            nextNumber = Integer.parseInt(numberPart) + 1;
        }

        return prefix + String.format("%04d", nextNumber);
    }
}
