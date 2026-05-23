package school.management.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.management.entity.EducationEntity;
import school.management.entity.EmergencyContactEntity;
import school.management.entity.EmployeeRegister;
import school.management.entity.ExperienceDetails;
import school.management.repository.EducationRepo;
import school.management.repository.EmergencyContactRepo;
import school.management.repository.EmployeeRepo;
import school.management.repository.ExperienceDetailsRepo;
import school.management.service.EmployeeService;

import javax.swing.text.html.Option;
import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private EducationRepo educationRepo;

    @Autowired
    private ExperienceDetailsRepo experienceRepo;

    @Autowired
    private EmergencyContactRepo emergencyContactRepo;


    @Override
    public Map<String, Object> saveEmployee(EmployeeRegister employee) {
        employeeRepo.save(employee);
        return Map.of("message", "Employee saved successfully!!",
                "status", true);
    }

    @Override
    public List<EmployeeRegister> findByAllRolecodeIn(List<String> roles) {
        return employeeRepo.findByRoleCodeIn(roles).isEmpty()? List.of() : employeeRepo.findByRoleCodeIn(roles);
    }

    @Override
    public Optional<EmployeeRegister> employeeFindById(String id) {
        Optional<EmployeeRegister> emp = employeeRepo.findById(id);
        if(emp.isPresent()){
            return emp;
        }
        return emp;
    }

    @Override
    public Map<String, Object> selfOnboardingEmployee(Map<String, Object> request) {

        try {
            // ✅ Extract sections safely
            Map<String, Object> empDetails =
                    (Map<String, Object>) request.getOrDefault("personal", new HashMap<>());

            List<Map<String, Object>> educationalDetails =
                    (List<Map<String, Object>>) request.getOrDefault("education", new ArrayList<>());

            List<Map<String, Object>> experienceDetails =
                    (List<Map<String, Object>>) request.getOrDefault("experience", new ArrayList<>());

            List<Map<String, Object>> emergencyContacts =
                    (List<Map<String, Object>>) request.getOrDefault("emergencyContacts", new ArrayList<>());

            String userId = (String) request.get("userId");

            Optional<EmployeeRegister> existEmp = employeeRepo.findById(userId);

            if (existEmp.isEmpty()) {
                return Map.of(
                        "status", false,
                        "message", "Employee not found"
                );
            }

            EmployeeRegister emp = existEmp.get();

            emp.setOnboardingStatus("PEN");
            emp.setAadhaarNumber((String) empDetails.get("aadhaar"));
            emp.setCurrentAddress((String) empDetails.get("currentAddress"));
            emp.setPermanentAddress((String) empDetails.get("permanentAddress"));
            emp.setPanNumber((String) empDetails.get("pan"));
            emp.setNationality((String) empDetails.get("nationality"));
            emp.setGender((String) empDetails.get("gender"));
            emp.setMaritalStatus((String) empDetails.get("maritalStatus"));
            employeeRepo.save(emp);
            List<EducationEntity> eduList = educationalDetails.stream().map(ele -> {
                EducationEntity education = new EducationEntity();
                education.setEmpId(emp.getEmpId());
                education.setCourse((String) ele.get("degree"));
                education.setGPA((String) ele.get("grade"));
                education.setPlace((String) ele.get("place"));
                education.setYearOfPassing((String) ele.get("year"));
                education.setInstituteName((String) ele.get("institution"));
                education.setPlace("Guntur");
                return education;
            }).toList();
            educationRepo.saveAll(eduList);
            List<ExperienceDetails> expList = experienceDetails.stream().map(ele -> {
                ExperienceDetails exp = new ExperienceDetails();
                exp.setEmpId(emp.getEmpId());
                exp.setCompanyName((String) ele.get("company"));
                exp.setCompanyContactDetails((String) ele.get("companyContact"));
                exp.setTotalService((String) ele.get("totalService"));
                exp.setHrName((String) ele.get("hrName"));
                exp.setManagerName((String) ele.get("managerName"));
                exp.setDesignation((String) ele.get("role"));
                exp.setJoiningDate((String) ele.get("from"));
                exp.setExistDate((String) ele.get("to"));
                exp.setAddress((String) ele.get("location"));
                return exp;
            }).toList();
            experienceRepo.saveAll(expList);
            List<EmergencyContactEntity> contactList = emergencyContacts.stream().map(ele -> {
                EmergencyContactEntity contact = new EmergencyContactEntity();
                contact.setEmpId(emp.getEmpId());
                contact.setName((String) ele.get("name"));
                contact.setRelation((String) ele.get("relation"));
                contact.setContactDetails((String) ele.get("phone"));
                contact.setEmail((String) ele.get("email"));
                contact.setAddress((String) ele.get("address"));
                return contact;
            }).toList();
            emergencyContactRepo.saveAll(contactList);
            return Map.of(
                    "status", true,
                    "message", "Employee onboarding completed successfully"
            );

        } catch (Exception e) {
            e.printStackTrace();
            return Map.of(
                    "status", false,
                    "message", "Something went wrong",
                    "error", e.getMessage()
            );
        }
    }
}
