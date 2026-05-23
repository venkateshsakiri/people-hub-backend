package school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.management.entity.EmergencyContactEntity;

import java.util.List;

@Repository
public interface EmergencyContactRepo extends JpaRepository<EmergencyContactEntity,String> {

    List<EmergencyContactEntity> findByEmpId(String empId);
}
