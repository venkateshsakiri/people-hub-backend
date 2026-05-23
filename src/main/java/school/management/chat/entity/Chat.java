package school.management.chat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import school.management.entity.BaseEntity;

@Data
@Entity
@Table(name = "chat")
public class Chat {

    @Id
    private String id;

    private String type; // PRIVATE / GROUP

    private String name;
}
