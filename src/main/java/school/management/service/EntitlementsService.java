package school.management.service;

import school.management.entity.EntitlementsEntity;

import java.util.List;

public interface EntitlementsService {

    String saveEntitlements(EntitlementsEntity entitle);

    List<EntitlementsEntity> getAllEntitlementsByRole(String role);
}
