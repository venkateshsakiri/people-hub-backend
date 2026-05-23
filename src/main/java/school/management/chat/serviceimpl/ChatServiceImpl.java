package school.management.chat.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.management.chat.entity.Chat;
import school.management.chat.entity.ChatParticipant;
import school.management.chat.entity.Message;
import school.management.chat.repo.ChatParticipantRepo;
import school.management.chat.repo.ChatRepo;
import school.management.chat.repo.MessageRepo;
import school.management.chat.service.ChatService;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepo chatRepo;

    @Autowired
    private ChatParticipantRepo participantRepo;

    @Autowired
    private MessageRepo messageRepo;

    // ✅ SEND MESSAGE
    @Override
    public Map<String, Object> sendMessage(Map<String, Object> request) {

        String senderId = (String) request.get("senderId");
        String receiverId = (String) request.get("receiverId");
        String content = (String) request.get("message");

        Optional<String> existingChat = participantRepo.findCommonChatId(senderId, receiverId);

        String chatId;

        if (existingChat.isPresent()) {
            chatId = existingChat.get();
        } else {
            Chat chat = new Chat();
            chat.setId(UUID.randomUUID().toString());
            chat.setType("PRIVATE");
            chatRepo.save(chat);

            participantRepo.save(new ChatParticipant(chat.getId(), senderId));
            participantRepo.save(new ChatParticipant(chat.getId(), receiverId));

            chatId = chat.getId();
        }

        // 🔹 Save message
        Message msg = new Message();
        msg.setId(UUID.randomUUID().toString());
        msg.setChatId(chatId);
        msg.setSenderId(senderId);
        msg.setContent(content);
        msg.setType("TEXT");
        msg.setStatus("SENT");
        msg.setCreatedAt(LocalDateTime.now());

        messageRepo.save(msg);

        return Map.of(
                "status", true,
                "message", "Message sent",
                "data", msg
        );
    }

    // ✅ GET CONVERSATION
    @Override
    public Map<String, Object> getConversation(String senderId, String receiverId) {

        List<ChatParticipant> senderChats = participantRepo.findByUserId(senderId);

        List<Message> messages = new ArrayList<>();

        for (ChatParticipant cp : senderChats) {
            List<ChatParticipant> participants = participantRepo.findByChatId(cp.getChatId());

            boolean match = participants.stream()
                    .anyMatch(p -> p.getUserId().equals(receiverId));

            if (match) {
                messages = messageRepo.findByChatIdOrderByCreatedAtAsc(cp.getChatId());
                break;
            }
        }

        return Map.of(
                "status", true,
                "data", messages
        );
    }

    // ✅ LATEST CHATS (SIDEBAR)
    @Override
    public Map<String, Object> getLatestChats(String userId) {

        List<ChatParticipant> chats = participantRepo.findByUserId(userId);

        List<Map<String, Object>> response = new ArrayList<>();

        for (ChatParticipant cp : chats) {

            List<Message> msgs = messageRepo.findByChatIdOrderByCreatedAtAsc(cp.getChatId());

            if (!msgs.isEmpty()) {
                Message lastMsg = msgs.get(msgs.size() - 1);

                Map<String, Object> map = new HashMap<>();
                map.put("chatId", cp.getChatId());
                map.put("lastMessage", lastMsg.getContent());
                map.put("time", lastMsg.getCreatedAt());

                response.add(map);
            }
        }

        return Map.of(
                "status", true,
                "data", response
        );
    }

    // ✅ MARK AS READ
    @Override
    public Map<String, Object> markMessagesAsRead(String senderId, String receiverId) {

        List<ChatParticipant> chats = participantRepo.findByUserId(senderId);

        for (ChatParticipant cp : chats) {
            List<ChatParticipant> participants = participantRepo.findByChatId(cp.getChatId());

            boolean match = participants.stream()
                    .anyMatch(p -> p.getUserId().equals(receiverId));

            if (match) {
                List<Message> msgs = messageRepo.findByChatIdOrderByCreatedAtAsc(cp.getChatId());

                for (Message msg : msgs) {
                    msg.setStatus("READ");
                }

                messageRepo.saveAll(msgs);
            }
        }

        return Map.of(
                "status", true,
                "message", "Messages marked as read"
        );
    }
}