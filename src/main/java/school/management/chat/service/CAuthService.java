package school.management.chat.service;


import school.management.chat.dto.AuthResponse;
import school.management.chat.dto.LoginRequest;
import school.management.chat.dto.RegisterRequest;

public interface CAuthService {

    AuthResponse register(RegisterRequest req);
    AuthResponse login(LoginRequest req);
}
