package edu.poly.mxh.Repository;

import edu.poly.mxh.Modal.Conversations;
import edu.poly.mxh.Modal.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Messages,Long> {
    List<Messages> findAllByConversations(Conversations conversation);

    @Query(value = "SELECT * FROM messages WHERE conversation_id = ?1 ORDER BY create_at DESC LIMIT 1", nativeQuery = true)
    Optional<Messages> getLatestMessage(Long id);
}
