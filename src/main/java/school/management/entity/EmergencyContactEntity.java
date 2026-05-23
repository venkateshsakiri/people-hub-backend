package school.management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "emergency_contact")
public class EmergencyContactEntity extends BaseEntity{

    private String empId;

    private String name;

    private String relation;

    private String contactDetails;

    private String email;

    private String address;
}
