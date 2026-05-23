package school.management.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Data
@MappedSuperclass
public class BaseEntity {

    @Id
    private String id;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "is_active")
    private Boolean isActive=true;

    @PrePersist
    protected void onCreate() {
        // ✅ Generate ID
        if (id == null) {
            String timestamp = new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
            int random = new Random().nextInt(90000) + 10000;
            this.id = timestamp + random;
        }

        // ✅ Set created date
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}