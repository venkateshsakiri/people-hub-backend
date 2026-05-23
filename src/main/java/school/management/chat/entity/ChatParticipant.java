package school.management.chat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "chat_participant")
public class ChatParticipant {
    @Id
    @GeneratedValue
    private Long id;

    private String chatId;

    private String userId;

    // ✅ Correct constructor
    public ChatParticipant(String chatId, String userId) {
        this.chatId = chatId;
        this.userId = userId;
    }

    // ✅ Default constructor (required by JPA)
    public ChatParticipant() {
    }
}
