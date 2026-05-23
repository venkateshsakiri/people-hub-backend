package school.management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class UserRegister extends BaseEntity{

    private String firstName;

    private String lastName;

    private String email;

    private String role;

    @Column(name = "role_code")
    private String roleCode;

    private String password;


}
