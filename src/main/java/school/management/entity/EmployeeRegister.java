package school.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "employee")
public class EmployeeRegister extends BaseEntity{

    private String firstName;

    private String lastName;

    private String empId;

    private String status;

    private String email;

    private boolean isActive=true;

    private String designation;

    private String department;

    private String dob;

    @Column(name = "reporting_manager")
    private String reportingManager;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "blood_group")
    private String bloodGroup;

    private String role;

    @Column(name = "role_code")
    private String roleCode;

    private String location;

    @Column(name = "joining_date")
    private String joiningDate;

    @Column(name = "onboarding_status")
    private String onboardingStatus;

    private String gender;

    @Column(name = "marital_status")
    private String maritalStatus;

    private String nationality;

    private String panNumber;

    private String aadhaarNumber;

    @Column(name = "current_address")
    private String currentAddress;

    @Column(name = "permanent_address")
    private String permanentAddress;

    @Column(name = "is_assets_alloted")
    private Boolean isAssetsAlloted = false;

}
