package school.management.controller;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.management.Response.PendingOnboardingEmployees;
import school.management.config.JwtUtil;
import school.management.entity.*;
import school.management.repository.*;
import school.management.service.EmployeeService;
import school.management.service.UserRegisterService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserRegisterService userService;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private DesignationRepo designationRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private LocationsRepo locationsRepo;

    @Autowired
    private SalaryRepo salaryRepo;

    @Autowired
    private ExperienceDetailsRepo experienceDetailsRepo;

    @Autowired
    private EducationRepo educationRepo;

    @Autowired
    private EmergencyContactRepo emergencyContactRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(value = "/emp/onboard", consumes = "application/json", produces = "application/json")
    private ResponseEntity<?> onboardingEmployee(@RequestBody Map<String, Object> request) {
        try {

            // ✅ USER
            UserRegister user = new UserRegister();
            user.setFirstName((String) request.get("firstName"));
            user.setLastName((String) request.get("lastName"));
            user.setRole((String) request.get("role"));
            user.setRoleCode(updateRoleCode((String) request.get("role")));
            user.setEmail((String) request.get("email"));
            user.setPassword("123456");

            userService.directUserRegister(user);

            // ✅ EMPLOYEE
            EmployeeRegister emp = new EmployeeRegister();
            emp.setId(user.getId());
            emp.setRole(user.getRole());
            emp.setRoleCode(user.getRoleCode());
            emp.setEmail(user.getEmail());
            emp.setFirstName(user.getFirstName());
            emp.setLastName(user.getLastName());

            String lastId = employeeRepo.findLastEmployeeId();
            String newId = userService.generateEmployeeId(lastId);
            emp.setEmpId(newId);

            emp.setDob((String) request.get("dob"));
            emp.setBloodGroup((String) request.get("bloodGroup"));
            emp.setDepartment((String) request.get("dep"));
            emp.setDesignation((String) request.get("designation"));
            emp.setLocation((String) request.get("location"));
            emp.setMobileNumber((String) request.get("mobile"));
            emp.setStatus("Active");
            emp.setOnboardingStatus("INP");
            emp.setReportingManager((String) request.get("repManager"));
            emp.setJoiningDate((String) request.get("joiningDate"));

            employeeService.saveEmployee(emp);

            // ✅ SALARY (FIXED)
            Map<String, Object> salaryMap = (Map<String, Object>) request.get("salary");

            SalaryEntity salary = new SalaryEntity();
            salary.setEmpId(emp.getEmpId()); // assuming relation

            salary.setAnnualCtc(salaryMap.get("ctc").toString());
            salary.setBasicPay(salaryMap.get("basic").toString());
            salary.setHra(salaryMap.get("hra").toString());
            salary.setSpecialAllowance(salaryMap.get("specialAllowance").toString());
            salary.setPf(salaryMap.get("pf").toString());
            salary.setVariablePay(salaryMap.get("variablePay").toString());
            salary.setPayCycle((String) salaryMap.get("payCycle"));

            salaryRepo.save(salary);

            Map<String, Object> response = new HashMap<>();
            response.put("status", true);
            response.put("message", "Employee Onboarded Successfully!");
            response.put("data", emp);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @GetMapping(value = "/common/meta/data")
    private ResponseEntity<?> getCommonMetaData(){
        try{
            List<DesignationEntity> designationList = designationRepo.findAll();
            List<LocationsEntity> locationsList = locationsRepo.findAll();
            List<Departments> departmentsList = departmentRepo.findAll();
            List<String> roles = List.of("SUPERADMIN", "HRADMIN", "MANAGER", "LEAD");
            List<EmployeeRegister> listOfRm = employeeService.findByAllRolecodeIn(roles);
            // Transform Designations
            List<String> designations = designationList.stream()
                    .map(DesignationEntity::getValue)
                    .toList();

            // Transform Departments
            List<String> departments = departmentsList.stream()
                    .map(Departments::getValue) // adjust field name if different
                    .toList();

            // Transform Locations
            List<String> locations = locationsList.stream()
                    .map(LocationsEntity::getValue) // adjust field name if different
                    .toList();

            // Transform Reporting Managers
            List<Map<String, Object>> reportingManagers = listOfRm.stream().map(emp -> {
                Map<String, Object> rm = new HashMap<>();
                rm.put("empId", emp.getEmpId());
                rm.put("firstName", emp.getFirstName());
                rm.put("lastName", emp.getLastName());
                rm.put("designation", emp.getDesignation()); // adjust if needed
                return rm;
            }).toList();

            // Final Data Object
            Map<String, Object> data = new HashMap<>();
            data.put("designation", designations);
            data.put("department", departments);
            data.put("locations", locations);
            data.put("reportingManagers", reportingManagers);

            // Final Response
            Map<String, Object> response = new HashMap<>();
            response.put("status", true);
            response.put("message", "common data fetched successfully!");
            response.put("data", data);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/emp/self-onboard",consumes = "application/json",produces = "application/json")
    private ResponseEntity<?> selfOnboarding(@RequestBody Map<String,Object> request){
        try{
            return ResponseEntity.ok(employeeService.selfOnboardingEmployee(request));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/emp/current-user")
    private ResponseEntity<?> getCurrentEmpInfo( @RequestHeader("Authorization") String authHeader){
        try{
            String token = authHeader.substring(7);
            Claims claims = jwtUtil.extractAllClaims(token);
            String userId = (String) claims.get("userId");
            Optional<EmployeeRegister> emp = employeeService.employeeFindById(userId);
            Map<String,Object> response = new HashMap<>();
            if(emp.isPresent()){
                response.put("status",true);
                response.put("message","Employee details fetched successfully!");
                response.put("data",emp.get());
            }else{
                response.put("status",false);
                response.put("message","Employee details not found");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping(value = "/get-pending-onboarding-employees")
    private ResponseEntity<?> OnboardingPendingEmployees( @RequestHeader("Authorization") String authHeader){
        try{
            String token = authHeader.substring(7);
            Claims claims = jwtUtil.extractAllClaims(token);
            String userRole = (String) claims.get("role");
            if(!"HR ADMIN".equalsIgnoreCase(userRole)){
                Map<String,Object> res = new HashMap<>();
                res.put("status",false);
                res.put("message","User doesn't have permission to get data");
                return ResponseEntity.ok(res);
            }
            List<EmployeeRegister> list = employeeRepo.getOnboardingEmployees("PEN");
            List<PendingOnboardingEmployees> pendingResList = list.stream().map(ele ->{
                PendingOnboardingEmployees pendingRes = new PendingOnboardingEmployees();
                pendingRes.setId(ele.getId());
                pendingRes.setUserId(ele.getId());
                pendingRes.setEmpId(ele.getEmpId());
                pendingRes.setEmail(ele.getEmail());
                pendingRes.setDesignation(ele.getDesignation());
                pendingRes.setDepartment(ele.getDepartment());
                pendingRes.setFullName(ele.getFirstName()+" "+ele.getLastName());
                pendingRes.setSubmittedAt(ele.getCreatedAt().toString());
                pendingRes.setStatus(ele.getOnboardingStatus().equalsIgnoreCase("PEN")? "PENDING" : ele.getOnboardingStatus().equalsIgnoreCase("CNF")? "APPROVED" : ele.getOnboardingStatus().equalsIgnoreCase("REJ")? "REJECTED" :"");
                PendingOnboardingEmployees.RequiredPayload payload =  new PendingOnboardingEmployees.RequiredPayload();
                PendingOnboardingEmployees.PersonalDetails personal = new PendingOnboardingEmployees.PersonalDetails();
                personal.setFirstName(ele.getFirstName());
                personal.setLastName(ele.getLastName());
                personal.setEmail(ele.getEmail());
                personal.setMobile(ele.getMobileNumber());
                personal.setDob(ele.getDob());
                personal.setBloodGroup(ele.getBloodGroup());
                personal.setGender(ele.getGender());
                personal.setMaritalStatus(ele.getMaritalStatus());
                personal.setNationality(ele.getNationality());
                personal.setCurrentAddress(ele.getCurrentAddress());
                personal.setPermanentAddress(ele.getPermanentAddress());
                personal.setPan(ele.getPanNumber());
                personal.setAadhaar(ele.getAadhaarNumber());
                payload.setPersonal(personal);
                List<EducationEntity> eduList = educationRepo.findByEmpId(ele.getEmpId());
                List<PendingOnboardingEmployees.EducationalDetails> eduRes = eduList.stream().map(ed->{
                    PendingOnboardingEmployees.EducationalDetails e =
                            new PendingOnboardingEmployees.EducationalDetails();
                    e.setDegree(ed.getCourse());
                    e.setInstitution(ed.getInstituteName());
                    e.setPlace(ed.getPlace());
                    e.setYear(ed.getYearOfPassing());
                    e.setGrade(ed.getGPA());
                    return e;
                }).toList();
                payload.setEducation(eduRes);
                List<EmergencyContactEntity> emergencyList = emergencyContactRepo.findByEmpId(ele.getEmpId());
                List<PendingOnboardingEmployees.EmergencyContacts> emergencyRes = emergencyList.stream().map(em->{
                    PendingOnboardingEmployees.EmergencyContacts e = new PendingOnboardingEmployees.EmergencyContacts();
                    e.setEmail(em.getEmail());
                    e.setName(em.getName());
                    e.setAddress(em.getAddress());
                    e.setPhone(em.getContactDetails());
                    e.setRelation(em.getRelation());
                    return e;
                }).toList();
                payload.setEmergencyContacts(emergencyRes);
                List<ExperienceDetails> expList = experienceDetailsRepo.findByEmpId(ele.getEmpId());
                List<PendingOnboardingEmployees.ExperienceData> expRes =
                        expList.stream().map(ex -> {
                            PendingOnboardingEmployees.ExperienceData e =
                                    new PendingOnboardingEmployees.ExperienceData();

                            e.setCompany(ex.getCompanyName());
                            e.setRole(ex.getDesignation());
                            e.setFrom(ex.getJoiningDate());
                            e.setTo(ex.getExistDate());
                            e.setLocation(ex.getAddress());
                            e.setHrName(ex.getHrName());
                            e.setManagerName(ex.getManagerName());
                            e.setTotalService(ex.getTotalService());
                            e.setCompanyContact(ex.getCompanyContactDetails());
                            return e;
                        }).toList();

                payload.setExperience(expRes);
                pendingRes.setPayload(payload);
                return pendingRes;
            }).toList();
            Map<String,Object> response = new HashMap<>();
            response.put("status",true);
            response.put("message","Pending employees fetched successfully!");
            response.put("data",pendingResList);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/emp/onboarding/approve",consumes = "application/json",produces = "application/json")
    private ResponseEntity<?> onboardingStatusApproveOrReject(@RequestBody Map<String,Object> request){
        try{
            String id = request.get("id").toString();
            Optional<EmployeeRegister> emp = employeeService.employeeFindById(id);
            Map<String,Object> res = new HashMap<>();
            if(emp.isPresent()){
                String status = request.get("action").toString(); // REJECTED, APPROVED
                emp.get().setOnboardingStatus("APPROVED".equalsIgnoreCase(status)? "CNF" : "REJ");
                employeeRepo.save(emp.get());
                res.put("status",true);
                res.put("message","Status updated successfully!");
                return ResponseEntity.ok(res);
            }
            res.put("status",false);
            res.put("message","Employee is not found!");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private String updateRoleCode(String role){
        if(role.equalsIgnoreCase("SUPER ADMIN")){
            return "SUPERADMIN";
        }else if(role.equalsIgnoreCase("HR ADMIN")){
            return "HRADMIN";
        }else if(role.equalsIgnoreCase("MANAGER")){
            return "MANAGER";
        }else if(role.equalsIgnoreCase("LEAD")){
            return "LEAD";
        }else if(role.equalsIgnoreCase("IT ADMIN")){
            return "ITADMIN";
        }else if(role.equalsIgnoreCase("EMP")){
            return "EMP";
        }
        return "";
    }
}
