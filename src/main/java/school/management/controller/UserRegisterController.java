package school.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.management.Response.LoginResponse;
import school.management.config.JwtUtil;
import school.management.config.SecurityConfig;
import school.management.entity.AccessRightsEntity;
import school.management.entity.EmployeeRegister;
import school.management.entity.EntitlementsEntity;
import school.management.entity.UserRegister;
import school.management.service.AccessRightsService;
import school.management.service.EmployeeService;
import school.management.service.EntitlementsService;
import school.management.service.UserRegisterService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserRegisterController {

    @Autowired
    public UserRegisterService userService;

    @Autowired
    public EmployeeService employeeService;

    @Autowired
    public AccessRightsService accessRightsService;

    @Autowired
    private EntitlementsService entitlementsService;

    @Autowired
    private SecurityConfig passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping(value = "/user-register",consumes = "application/json",produces = "application/json")
    public ResponseEntity<Map<String, Object>> userRegister(@RequestBody UserRegister user){
        try {
            Optional<UserRegister> existUsr = userService.existUser(user.getEmail());
            if(existUsr.isPresent()){
                return ResponseEntity.ok(Map.of("message","Email alreday exists","status",false));
            }
            Map<String, Object> response = userService.saveUser(user);
//            if(response.get("id") != null){
//                EmployeeRegister emp = new EmployeeRegister();
//                String id = response.get("id");
//                emp.setId(id);
//                employeeService.saveEmployee()
//            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/login",consumes = "application/json",produces = "application/json")
    public ResponseEntity<?> userLogin(@RequestBody Map<String, String> request){

        try {
            String email = request.get("email");
            String password = request.get("password");
            Optional<UserRegister> optionalUser = userService.existUser(email);
            if(optionalUser.isPresent()){
                UserRegister user = optionalUser.get();
                if(passwordEncoder.passwordEncoder().matches(password, user.getPassword())){
                    String token = jwtUtil.generateToken(user);
                    LoginResponse response = new LoginResponse();
                    response.setToken(token);
                    response.setMessage("user loggined successfully!");
                    response.setStatus(true);
                    Optional<EmployeeRegister> emp = employeeService.employeeFindById(user.getId());
                    LoginResponse.EmployeeData responseData = new LoginResponse.EmployeeData();
                    responseData.setEmail(user.getEmail());
                    responseData.setFirstName(user.getFirstName());
                    responseData.setLastName(user.getLastName());
                    responseData.setRole(user.getRole());
                    responseData.setCreatedAt(user.getCreatedAt().toString());
                    responseData.setId(user.getId());
                    responseData.setEmpId(emp.get().getEmpId());
                    responseData.setDepartment(emp.get().getDepartment());
                    responseData.setDesignation(emp.get().getDesignation());
                    responseData.setAvatarSeed(emp.get().getLastName());
                    responseData.setStatus(emp.get().getStatus());
                    responseData.setOnboardingStatus(emp.get().getOnboardingStatus());
                    responseData.setUpdatedAt( user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : "");
                    List<AccessRightsEntity> rights = accessRightsService.getAllAccessRightsBasedOnRole(user.getRoleCode());
                    List<LoginResponse.AccessRight> accessList = rights.stream().map(ele -> {
                        LoginResponse.AccessRight ar = new LoginResponse.AccessRight();
                        ar.setModule(ele.getModule());
                        ar.setView(Boolean.TRUE.equals(ele.getIsView()));
                        ar.setEdit(Boolean.TRUE.equals(ele.getIsEdit()));
                        ar.setDelete(Boolean.TRUE.equals(ele.getIsDelete()));
                        ar.setCreate(Boolean.TRUE.equals(ele.getIsCreate()));
                        ar.setApprove(Boolean.TRUE.equals(ele.getIsApprove()));
                        return ar;
                    }).toList();
                    Map<String, List<LoginResponse.AccessRight>> accessMap = new HashMap<>();
                    accessMap.put(user.getRoleCode(), accessList);
                    responseData.setAccessRights(accessMap);
                    List<EntitlementsEntity> enetitles = entitlementsService.getAllEntitlementsByRole(user.getRoleCode());
                    List<LoginResponse.Entitlement> entitleList = enetitles.stream().map(ele ->{
                        LoginResponse.Entitlement ent = new LoginResponse.Entitlement();
                        ent.setGroup(ele.getGroup());
                        ent.setIcon(ele.getIcon());
                        ent.setPath(ele.getPath());
                        ent.setId(ele.getId());
                        ent.setTitle(ele.getTitle());
                        return ent;
                    }).toList();
                    responseData.setEntitlements(entitleList);
                    response.setData(responseData);
                    return ResponseEntity.ok(response);
                }else{
                    Map<String, Object> response = new HashMap<>();
                    response.put("status",false);
                    response.put("message", "invalid email or password");
                    return ResponseEntity.ok(response);
                }
            }
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    @Value("${username}")
//    private String username;
//
//    @Value("${host}")
//    private String host;
//
//    @GetMapping(value = "/test")
//    public String test() {
//        return "Username = " + username + " Host = " + host;
//    }
}
