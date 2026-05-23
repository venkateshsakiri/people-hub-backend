package school.management.chat.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import school.management.chat.entity.ChatParticipant;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatParticipantRepo extends JpaRepository<ChatParticipant,String> {
    List<ChatParticipant> findByUserId(String userId);

    List<ChatParticipant> findByChatId(String chatId);

    @Query("""
    SELECT cp1.chatId
    FROM ChatParticipant cp1
    WHERE cp1.userId = :user1
      AND cp1.chatId IN (
          SELECT cp2.chatId
          FROM ChatParticipant cp2
          WHERE cp2.userId = :user2
      )
""")
    Optional<String> findCommonChatId(@Param("user1") String user1,
                                      @Param("user2") String user2);
}
