package edu.poly.mxh.Repository;

import edu.poly.mxh.Modal.Post;
import edu.poly.mxh.Modal.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUsers(UserProfile users);
    List<Post> findByPostType(String postType);

    @Query(value = "select * from post where user_id = ?1 and post_type = 'Công khai'", nativeQuery = true)
    List<Post> findAllFriendPublicPost(Long id);
}
