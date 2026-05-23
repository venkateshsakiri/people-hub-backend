package school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.management.entity.AssetsEntity;

@Repository
public interface AssetRepo extends JpaRepository<AssetsEntity,String> {
}
