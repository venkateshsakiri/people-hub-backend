package school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.management.entity.BirthdayWish;

@Repository
public interface BirthdayWishes extends JpaRepository<BirthdayWish,Long> {
}
