package school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.management.entity.AccessRightsEntity;

import java.util.List;

@Repository
public interface AccessRightsRepo extends JpaRepository<AccessRightsEntity,String> {

    List<AccessRightsEntity> findByRole(String role);

}
