package school.management.chat.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.management.chat.dto.SendMessageRequest;
import school.management.chat.entity.Message;
import school.management.chat.repo.MessageRepo;
import school.management.chat.service.MessageService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepo repo;

//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;

    @Override
    public Message sendMessage(String userId, SendMessageRequest req) {

        Message msg = new Message();
        msg.setId(UUID.randomUUID().toString());
        msg.setChatId(req.getChatId());
        msg.setSenderId(userId);
        msg.setContent(req.getContent());
        msg.setType(req.getType());
        msg.setStatus("SENT");
        msg.setCreatedAt(LocalDateTime.now());

        repo.save(msg);

        // WebSocket push
//        messagingTemplate.convertAndSend(
//                "/topic/chat/" + req.getChatId(),
//                msg
//        );

        return msg;
    }
}
