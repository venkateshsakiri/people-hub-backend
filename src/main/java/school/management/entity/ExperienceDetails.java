package school.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "experience")
public class ExperienceDetails extends BaseEntity{

    private String empId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "joining_date")
    private String joiningDate;

    @Column(name = "exist_date")
    private String existDate;

    @Column(name = "total_service")
    private String totalService;

    private String address;

    @Column(name = "hr_name")
    private String hrName;

    @Column(name = "manager_name")
    private String managerName;

    private String designation;

    @Column(name = "company_contact_details")
    private String companyContactDetails;

}
