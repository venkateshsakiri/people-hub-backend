package school.management.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.management.entity.AccessRightsEntity;
import school.management.repository.AccessRightsRepo;
import school.management.service.AccessRightsService;

import java.util.List;

@Service
public class AccessRightsServiceImpl implements AccessRightsService {


    @Autowired
    private AccessRightsRepo accessRightsRepo;


    @Override
    public String saveAccessRights(AccessRightsEntity accessRights) {
        accessRightsRepo.save(accessRights);
        return "Access rights added successfully!";
    }

    @Override
    public List<AccessRightsEntity> getAllAccessRightsBasedOnRole(String role) {
        List<AccessRightsEntity> list = accessRightsRepo.findByRole(role);
        return list.isEmpty() ? List.of() : list;
    }
}
