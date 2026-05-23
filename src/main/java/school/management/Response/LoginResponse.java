package school.management.Response;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class LoginResponse {

    private Boolean status;

    private String message;

    private String token;

    private EmployeeData data;

    @Data
    public static class EmployeeData {

        private String id;
        private String createdAt;
        private String updatedAt;

        private String firstName;
        private String lastName;
        private String email;
        private String role;
        private String department;
        private String designation;
        private String avatarSeed;
        private String empId;
        private String status;
        private String onboardingStatus;

        private List<Entitlement> entitlements;

        private Map<String, List<AccessRight>> accessRights;
    }

    @Data
    public static class Entitlement {

        private String id;
        private String title;
        private String path;
        private String icon;
        private String group;
    }

    @Data
    public static class AccessRight {

        private String module;
        private boolean view;
        private boolean create;
        private boolean edit;
        private boolean delete;
        private boolean approve;
    }
}
