package school.management.Response;

import lombok.Data;

import java.util.List;

@Data
public class PendingOnboardingEmployees {

    private String id;

    private String userId;

    private String empId;

    private String email;

    private String fullName;

    private String designation;

    private String department;

    private String submittedAt;

    private String status;

    private RequiredPayload payload;

    @Data
    public static class RequiredPayload{

        private PersonalDetails personal;

        private List<EducationalDetails> education;

        private List<ExperienceData> experience;

        private List<EmergencyContacts> emergencyContacts;
    }

    @Data
    public static class PersonalDetails{
        private String firstName;
        private String lastName;
        private String email;
        private String mobile;
        private String dob;
        private String bloodGroup;
        private String gender;
        private String maritalStatus;
        private String nationality;
        private String currentAddress;
        private String permanentAddress;
        private String pan;
        private String aadhaar;
    }

    @Data
    public static class EducationalDetails{
        private String degree;
        private String institution;
        private String place;
        private String year;
        private String grade;
    }

    @Data
    public static class ExperienceData{
        private String company;
        private String role;
        private String from;
        private String to;
        private String location;
        private String hrName;
        private String managerName;
        private String companyContact;
        private String totalService;
    }
    @Data
    public static class EmergencyContacts{
        private String name;
        private String relation;
        private String phone;
        private String email;
        private String address;
    }

}
