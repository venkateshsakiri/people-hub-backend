package school.management.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import school.management.entity.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "cusers")
public class Cusers {

    @Id
    private String id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String role;

    private String status; // ONLINE / OFFLINE

    private LocalDateTime lastSeen;
}
