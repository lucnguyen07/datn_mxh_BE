package edu.poly.mxh.Service;

import edu.poly.mxh.Modal.Comment;
import edu.poly.mxh.Modal.Notification;
import edu.poly.mxh.Modal.Post;
import edu.poly.mxh.Repository.CommentRepository;
import edu.poly.mxh.Repository.NotificationsRepository;
import edu.poly.mxh.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private NotificationsRepository notificationsRepository;

    @Override
    @Transactional
    public void save(Comment comment) {
        comment.setCreatAt(LocalDateTime.now());
        comment.setRepCommentId(comment.getRepCommentId());
        // Cập nhật số lượng comment của bài đăng
        Long postId = comment.getPost().getPostId();
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            post.setComentCount(post.getComentCount() + 1);
            postRepository.save(post);
        }
        //Kiểm tra người đăng bài có trùng với người comment ko
        if (post.getUsers().getUserId() != comment.getUsers().getUserId()){
            notificationsRepository.createNotification(comment.getUsers().getUserId(),post.getUsers().getUserId(), post.getPostId(),
                    " đã bình luận về bài viết của bạn", "comment");
        }
        commentRepository.save(comment);
    }

//    @Override
//    public void deleteAllByPost(Post post) {
//        commentRepository.deleteAllByIdPost(post);
//    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findAllByPost(Post post) {
        return commentRepository.findAllByPost(post.getPostId());
    }

    @Override
    public Long countPostComment(Long postId) {
        return postRepository.findById(postId).get().getComentCount();
    }

    @Override
    public List<Comment> getReplies(Long repCommentId) {
        return commentRepository.findByRepCommentId(repCommentId);
    }

//    @Override
//    public Long countPostCommentbyComment(Post post) {
//        return commentRepository.countByPost(post.getPostId());
//    }
}
