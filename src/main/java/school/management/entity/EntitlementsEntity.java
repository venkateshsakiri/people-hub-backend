package school.management.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "entitlements",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"id", "role"})
        })
public class EntitlementsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Column(name = "id")
    private String id;

    private String role;

    private String title;

    private String path;

    private String icon;

    @Column(name = "group_name")
    private String group;
}
