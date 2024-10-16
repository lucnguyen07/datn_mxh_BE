package edu.poly.mxh.Repository;

import edu.poly.mxh.Modal.Conversations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConversationsRepository extends JpaRepository<Conversations,Long> {
    @Modifying
    @Query(value = "insert into conversations(type) values(1)", nativeQuery = true)
    void createPersonalConversation();

    @Query(value = "SELECT DISTINCT c.* " +
            "FROM Conversations c " +
            "INNER JOIN Participants p1 ON c.conversation_id = p1.conversation_id " +
            "INNER JOIN Participants p2 ON c.conversation_id = p2.conversation_id " +
            "WHERE c.type = 1 AND p1.user_id = ?1 AND p2.user_id = ?2", nativeQuery = true)
    Optional<Conversations> findPersonalConversation(Long id1, Long id2);


    @Query(value = "SELECT * FROM conversations ORDER BY conversation_id DESC LIMIT 1", nativeQuery = true)
    Conversations getNewConversation();

    @Query(value = "SELECT c.* FROM conversations c JOIN participants cm ON c.conversation_id = cm.conversation_id WHERE cm.user_id = ?1 AND c.type = 1", nativeQuery = true)
    List<Conversations> getAllPersonalConversation(Long id);

    @Modifying
    @Query(value = "insert into conversations(type) values(2)", nativeQuery = true)
    void createGroupConversation();

    @Query(value = "select c.* from conversations c join participants cm on c.conversation_id = cm.conversation_id where cm.user_id = ?1 and c.type = 2", nativeQuery = true)
    List<Conversations> getAllGroupConversation(Long id);

}
