package edu.poly.mxh.Repository;

import edu.poly.mxh.Modal.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationsRepository extends JpaRepository<Notification,Long> {
    @Modifying
    @Query(value = "insert into notification(user_id, user_id_recip, post_id, content, type, create_at, is_read) values(?1, ?2, ?3, ?4, ?5, time(now()), 0)", nativeQuery = true)
    void createNotification(Long userId, Long userIdRecip , Long postId, String content, String type);

    @Modifying
    @Query(value = "insert into notification(user_id, user_id_recip, content, type, create_at, is_read) values(?1, ?2, ?3, ?4, time(now()), 0)", nativeQuery = true)
    void createNotificationFriend(Long userId, Long userIdRecip, String content, String type);

    @Modifying
    @Query(value = "delete from notification where post_id = ?1 and type = ?2 ", nativeQuery = true)
    void deleteNotification(Long postId, String type);

    @Modifying
    @Query(value = "delete from notification where user_id = ?1 and user_id_recip = ?2 and type =?3 ", nativeQuery = true)
    void deleteNotificationFriend(Long userId, Long userIdRecip, String type);

    @Modifying
    @Query(value = "select * from notification " +
            "where user_id_recip = ?1", nativeQuery = true)
    List<Notification> getAll(Long userId);
}
