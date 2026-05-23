package school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import school.management.entity.EmployeeRegister;

import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<EmployeeRegister,String> {

    @Query(value = "SELECT emp_id FROM employee ORDER BY emp_id DESC LIMIT 1", nativeQuery = true)
    String findLastEmployeeId();

    List<EmployeeRegister> findByRoleCodeIn(List<String> roles);

    @Query(value = "SELECT e FROM EmployeeRegister e WHERE e.onboardingStatus = :status")
    List<EmployeeRegister> getOnboardingEmployees(@Param("status") String status);

}
