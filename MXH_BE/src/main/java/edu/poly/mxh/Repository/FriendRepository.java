package edu.poly.mxh.Repository;

import edu.poly.mxh.Modal.Friendship;
import edu.poly.mxh.Modal.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friendship,Long> {
    @Query(value = "select * from friendship where user_id_sender = ?1 and user_id_recipient = ?2 and status = 0" , nativeQuery = true)
    Optional<Friendship> findRequest(Long id1, Long id2);

    @Query(value = "select * from friendship where user_id_sender = ?1 and user_id_recipient = ?2 and status = 1" , nativeQuery = true)
    Optional<Friendship> checkFriend(Long id1, Long id2);

    @Modifying
    @Query(value = "delete from friendship where user_id_sender = ?1 and user_id_recipient = ?2", nativeQuery = true)
    void deleteFriendRequest(Long id1, Long id2);

    @Modifying
    @Query(value = "insert into friendship(user_id_sender, user_id_recipient, status) values(?1, ?2, true)", nativeQuery = true)
    void acceptFriendRequest(Long id1, Long id2);
}
