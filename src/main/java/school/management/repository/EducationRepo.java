package school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.management.entity.EducationEntity;

import java.util.List;

@Repository
public interface EducationRepo extends JpaRepository<EducationEntity,String> {

    List<EducationEntity> findByEmpId(String empId);
}
