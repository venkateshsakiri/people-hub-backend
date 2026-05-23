package school.management.chat.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import school.management.chat.entity.Message;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message,String> {
    List<Message> findByChatIdOrderByCreatedAtAsc(String chatId);

        // ✅ Conversation between 2 users
        @Query("SELECT m FROM Message m WHERE " +
                "(m.senderId = :senderId AND m.chatId IN " +
                "(SELECT cp.chatId FROM ChatParticipant cp WHERE cp.userId = :receiverId)) " +
                "OR " +
                "(m.senderId = :receiverId AND m.chatId IN " +
                "(SELECT cp.chatId FROM ChatParticipant cp WHERE cp.userId = :senderId)) " +
                "ORDER BY m.createdAt ASC")
        List<Message> getConversation(@Param("senderId") String senderId,
                                      @Param("receiverId") String receiverId);


        // ✅ Latest chats (last messages)
        @Query("SELECT m FROM Message m WHERE m.chatId IN " +
                "(SELECT cp.chatId FROM ChatParticipant cp WHERE cp.userId = :userId) " +
                "ORDER BY m.createdAt DESC")
        List<Message> getLatestChats(@Param("userId") String userId);


        // ✅ Mark as read
        @Modifying
        @Transactional
        @Query("UPDATE Message m SET m.status = 'READ' WHERE m.senderId = :senderId AND m.chatId IN " +
                "(SELECT cp.chatId FROM ChatParticipant cp WHERE cp.userId = :receiverId)")
        void markAsRead(@Param("senderId") String senderId,
                        @Param("receiverId") String receiverId);

}
