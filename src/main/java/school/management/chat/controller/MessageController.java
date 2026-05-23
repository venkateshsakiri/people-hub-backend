package school.management.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.management.chat.config.JwtUtil;
import school.management.chat.dto.SendMessageRequest;
import school.management.chat.service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService service;

    @Autowired
    @Qualifier("chatJwtUtil")
    private JwtUtil jwtUtil;

    @PostMapping("/send")
    public ResponseEntity<?> send(
            @RequestHeader("Authorization") String token,
            @RequestBody SendMessageRequest req) {

        String userId = jwtUtil.extractUserId(token.substring(7));

        return ResponseEntity.ok(service.sendMessage(userId, req));
    }
}
