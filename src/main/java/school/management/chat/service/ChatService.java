package school.management.chat.service;

import java.util.Map;

public interface ChatService {

    Map<String, Object> sendMessage(Map<String, Object> request);

    Map<String, Object> getConversation(String senderId, String receiverId);

    Map<String, Object> getLatestChats(String userId);

    Map<String, Object> markMessagesAsRead(String senderId, String receiverId);
}