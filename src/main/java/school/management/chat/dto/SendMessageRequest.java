package school.management.chat.dto;

import lombok.Data;

@Data
public class SendMessageRequest {

    private String chatId;
    private String content;
    private String type;
}
