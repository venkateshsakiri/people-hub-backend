package school.management.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "locations")
public class LocationsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String value;
}
