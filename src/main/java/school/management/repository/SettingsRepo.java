package school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.management.entity.Settings;

@Repository
public interface SettingsRepo extends JpaRepository<Settings,String> {
}
