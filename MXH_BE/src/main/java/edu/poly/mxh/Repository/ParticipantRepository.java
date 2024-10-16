package edu.poly.mxh.Repository;

import edu.poly.mxh.Modal.Participants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participants,Long> {
    @Modifying
    @Query(value = "insert into participants (conversation_id, user_id) values(?1, ?2)", nativeQuery = true)
    void addMember(Long conversationId, Long userId);

    @Query(value = "select * from participants cm where cm.user_id = ?1 and cm.conversation_id = ?2" , nativeQuery = true)
    Optional<Participants> checkUserInGroup(Long userId, Long CVId);
}
