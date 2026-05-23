package school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.management.entity.DesignationEntity;

@Repository
public interface DesignationRepo extends JpaRepository<DesignationEntity,Long> {
}
