package edu.poly.mxh.Repository;

import edu.poly.mxh.Modal.Comment;
import edu.poly.mxh.Modal.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query(value = "select * from comment where post_id = ?1", nativeQuery = true)
    List<Comment> findAllByPost(Long postId);

    List<Comment> findByRepCommentId(Long repCommentId);
    //Long countByPost(Post post);
}
