package school.management.service;

import school.management.entity.AccessRightsEntity;

import java.util.List;

public interface AccessRightsService {

    String saveAccessRights(AccessRightsEntity accessRights);

    List<AccessRightsEntity> getAllAccessRightsBasedOnRole(String role);
}
