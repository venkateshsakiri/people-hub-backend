package school.management.entity;

import jakarta.persistence.*;
import lombok.Data;
import software.amazon.awssdk.annotations.NotNull;

@Entity
@Data
@Table(name = "birthday")
public class BirthdayWish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    public String name;

    @NotNull
    @Column(length = 256)
    public String message;
}
