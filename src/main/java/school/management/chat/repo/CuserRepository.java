package school.management.chat.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.management.chat.entity.Cusers;

import java.util.Optional;

@Repository
public interface CuserRepository extends JpaRepository<Cusers,String> {

    Optional<Cusers> findByEmail(String email);
}
