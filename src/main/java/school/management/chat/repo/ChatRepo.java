package school.management.chat.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.management.chat.entity.Chat;

import java.util.Optional;

@Repository
public interface ChatRepo extends JpaRepository<Chat, String> {

    // ✅ Find chat by ID
    Optional<Chat> findById(String id);

    // ✅ Optional: find private chat by name (if you store something like user1_user2)
    Optional<Chat> findByName(String name);

}