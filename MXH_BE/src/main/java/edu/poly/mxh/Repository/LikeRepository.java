package edu.poly.mxh.Repository;

import edu.poly.mxh.Modal.Likes;
import edu.poly.mxh.Modal.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    boolean existsByPost_PostIdAndUsers_UserId(Long postId, Long userId);

    @Modifying
    @Query("DELETE FROM Likes l WHERE l.post.postId = :postId AND l.users.userId = :userId")
    void deleteByPost_PostIdAndUsers_UserId(Long postId, Long userId);

}
