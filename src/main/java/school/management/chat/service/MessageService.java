package school.management.chat.service;

import school.management.chat.dto.SendMessageRequest;
import school.management.chat.entity.Message;

public interface MessageService {

    Message sendMessage(String userId, SendMessageRequest req);
}
