package school.management.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.management.chat.config.JwtUtil;
import school.management.chat.service.ChatService;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final JwtUtil jwtUtil;

    // ✅ Send Message
    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, Object> request
    ) {
        String token = authHeader.substring(7);
        String senderId = (String) jwtUtil.extractAllClaims(token).get("userId");

        request.put("senderId", senderId);

        return ResponseEntity.ok(chatService.sendMessage(request));
    }

    // ✅ Get Conversation
    @GetMapping("/conversation")
    public ResponseEntity<?> getConversation(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam String receiverId
    ) {
        String token = authHeader.substring(7);
        String senderId = (String) jwtUtil.extractAllClaims(token).get("userId");

        return ResponseEntity.ok(chatService.getConversation(senderId, receiverId));
    }

    // ✅ Latest Chats
    @GetMapping("/latest-chats")
    public ResponseEntity<?> getLatestChats(
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.substring(7);
        String userId = (String) jwtUtil.extractAllClaims(token).get("userId");

        return ResponseEntity.ok(chatService.getLatestChats(userId));
    }

    // ✅ Mark as Read
    @PutMapping("/read")
    public ResponseEntity<?> markAsRead(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam String receiverId
    ) {
        String token = authHeader.substring(7);
        String senderId = (String) jwtUtil.extractAllClaims(token).get("userId");

        return ResponseEntity.ok(chatService.markMessagesAsRead(senderId, receiverId));
    }
}