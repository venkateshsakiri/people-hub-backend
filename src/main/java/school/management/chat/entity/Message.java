package school.management.chat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "message")
public class Message {

    @Id
    private String id;

    private String chatId;

    private String senderId;

    private String content;

    private String type;

    private String status;

    private LocalDateTime createdAt;

}
