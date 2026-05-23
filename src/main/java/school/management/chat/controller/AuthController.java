package school.management.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.management.chat.dto.LoginRequest;
import school.management.chat.dto.RegisterRequest;
import school.management.chat.service.CAuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private CAuthService authService;

    @PostMapping("/chat/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/chat/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }
}
