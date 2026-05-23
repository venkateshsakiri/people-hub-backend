package school.management.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.management.entity.DesignationEntity;
import school.management.repository.DesignationRepo;
import school.management.service.DesignationService;

@Service
public class DesignationServiceImpl implements DesignationService {

    @Autowired
    private DesignationRepo designationRepo;

    @Override
    public String saveDesignation(DesignationEntity designation) {
        designationRepo.save(designation);
        return "Designation saved successfully!";
    }
}
