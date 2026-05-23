package school.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "education")
public class EducationEntity extends BaseEntity{

    private String empId;

    private String instituteName;

    private String place;

    @Column(name = "year_of_passing")
    private String yearOfPassing;

    private String course;

    private String GPA;

}
