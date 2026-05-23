package school.management.chat.serviceimpl;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.management.chat.config.JwtUtil;
import school.management.chat.dto.AuthResponse;
import school.management.chat.dto.LoginRequest;
import school.management.chat.dto.RegisterRequest;
import school.management.chat.entity.Cusers;
import school.management.chat.repo.CuserRepository;
import school.management.chat.service.CAuthService;

import java.util.Date;
import java.util.UUID;


@Service
public class CAuthServiceImpl implements CAuthService {

    @Autowired
    public CuserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("chatJwtUtil")
    private JwtUtil jwtUtil;


    @Override
    public AuthResponse register(RegisterRequest req) {
        Cusers user = new Cusers();
        user.setId(UUID.randomUUID().toString());
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole("USER");

        userRepo.save(user);

        String token = jwtUtil.generateToken(user);

        return new AuthResponse(token, user.getId());
    }

    @Override
    public AuthResponse login(LoginRequest req) {
        Cusers user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user);

        return new AuthResponse(token, user.getId());
    }


}
