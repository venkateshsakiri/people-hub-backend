package school.management.service;

import school.management.entity.UserRegister;

import java.util.Map;
import java.util.Optional;

public interface UserRegisterService {

    Map<String,Object> saveUser(UserRegister user);

    Optional<UserRegister> existUser(String email);

    String directUserRegister(UserRegister user);


    String generateEmployeeId(String lastId);
}
