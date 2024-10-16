package edu.poly.mxh.Service;

import edu.poly.mxh.Modal.Comment;
import edu.poly.mxh.Modal.Post;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findById(Long id);
    List<Comment> findAllByPost(Post post);

    //void deleteAllByPost(Post post);
    List<Comment> getReplies(Long repCommentId);

    void save(Comment comment);
    Long countPostComment(Long postId);
    //Long countPostCommentbyComment(Post post);

}
