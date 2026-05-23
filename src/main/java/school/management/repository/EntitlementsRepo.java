package school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.management.entity.EntitlementsEntity;

import java.util.List;

public interface EntitlementsRepo extends JpaRepository<EntitlementsEntity,Long> {

    List<EntitlementsEntity> findByRole(String role);
}
