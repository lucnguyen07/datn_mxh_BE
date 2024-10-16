package edu.poly.mxh.Repository;

import edu.poly.mxh.Modal.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface UserRepository extends JpaRepository<UserProfile,Long> {
    UserProfile findByemail(String email);

    UserProfile findByotp(int otp);

    @Query(value = "SELECT user_profile.*, friendship.status AS friendship_status FROM user_profile " +
            "JOIN friendship ON user_profile.user_id = friendship.user_id_recipient " +
            "WHERE friendship.user_id_sender = ?1 " +
            "AND friendship.status = 1", nativeQuery = true)
    List<UserProfile> findFriendsByIdAndStatusTrue(Long id);

    @Query(value = "SELECT user_profile.*, friendship.status AS friendship_status FROM user_profile " +
            "JOIN friendship ON user_profile.user_id = friendship.user_id_sender " +
            "WHERE friendship.user_id_recipient = ?1 " +
            "AND friendship.status = 0", nativeQuery = true)
    List<UserProfile> listFriendRequest(Long id);

    @Query(value = "select user_profile.* from user_profile join participants cm on user_profile.user_id = cm.user_id where cm.conversation_id = ?1", nativeQuery = true)
    List<UserProfile> findMemberByConversation(Long id);

    @Query(value = "select * from user_profile where is_admin = '0'", nativeQuery = true)
    List<UserProfile> findAll();

    List<UserProfile> findUserProfileByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
}
