package school.management.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.management.entity.EntitlementsEntity;
import school.management.repository.EntitlementsRepo;
import school.management.service.EntitlementsService;

import java.util.List;

@Service
public class EntitlementsServiceImpl implements EntitlementsService {

    @Autowired
    private EntitlementsRepo entitlementsRepo;

    @Override
    public String saveEntitlements(EntitlementsEntity entitle) {
        entitlementsRepo.save(entitle);
        return "Entitlements save successfully!";
    }

    @Override
    public List<EntitlementsEntity> getAllEntitlementsByRole(String role) {
        List<EntitlementsEntity> list = entitlementsRepo.findByRole(role);
        return list.isEmpty()? List.of() : list;
    }
}
