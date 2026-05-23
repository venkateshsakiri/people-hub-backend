package school.management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "department")
public class Departments extends BaseEntity{

    private String code;

    private String value;

}
