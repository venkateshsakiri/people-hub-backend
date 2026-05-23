package school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.management.entity.ExperienceDetails;

import java.util.List;

@Repository
public interface ExperienceDetailsRepo extends JpaRepository<ExperienceDetails,String> {

    List<ExperienceDetails> findByEmpId(String empId);
}
