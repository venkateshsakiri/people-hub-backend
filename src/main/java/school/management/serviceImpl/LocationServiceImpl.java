package school.management.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.management.entity.LocationsEntity;
import school.management.repository.LocationsRepo;
import school.management.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {


    @Autowired
    private LocationsRepo locationsRepo;

    @Override
    public String saveLocations(LocationsEntity locations) {
        locationsRepo.save(locations);
        return "Location saved successfully!";
    }
}
