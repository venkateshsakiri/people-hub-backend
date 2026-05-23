package school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.management.entity.UserRegister;

import java.util.Optional;

@Repository
public interface UserRegisterRepo extends JpaRepository<UserRegister, String> {

    Optional<UserRegister> findByEmail(String email);
}
