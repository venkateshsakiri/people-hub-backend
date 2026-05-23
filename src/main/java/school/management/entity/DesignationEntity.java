package school.management.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "designation")
public class DesignationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    private String code;


}
