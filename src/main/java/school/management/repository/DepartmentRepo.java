package school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.management.entity.Departments;

@Repository
public interface DepartmentRepo extends JpaRepository<Departments,String> {

    boolean existsByCodeAndValue(String code, String value);
}
