package edu.poly.mxh.Service;

import edu.poly.mxh.Modal.Likes;
import edu.poly.mxh.Modal.Notification;
import edu.poly.mxh.Modal.Post;
import edu.poly.mxh.Modal.UserProfile;
import edu.poly.mxh.Repository.LikeRepository;
import edu.poly.mxh.Repository.NotificationsRepository;
import edu.poly.mxh.Repository.PostRepository;
import edu.poly.mxh.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeServiceImpl implements LikeService{
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private NotificationsRepository notificationsRepository;
    @Override
    public Long countPostLike(Long postId) {
        return postRepository.findById(postId).get().getLikeCount();
    }

    @Override
    @Transactional
    public void toggleLike(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        UserProfile users = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (likeRepository.existsByPost_PostIdAndUsers_UserId(postId, userId)) {
            likeRepository.deleteByPost_PostIdAndUsers_UserId(postId, userId);
            post.setLikeCount(post.getLikeCount() - 1);  // Giảm countLike
            notificationsRepository.deleteNotification(post.getPostId(), "like");
            postRepository.save(post);
        } else {
            Likes like = new Likes();
            like.setPost(post);
            like.setUsers(users);
            //Kiểm tra người đăng bài có trùng với người like ko
            if (post.getUsers().getUserId() != like.getUsers().getUserId()){
                notificationsRepository.createNotification(like.getUsers().getUserId(),post.getUsers().getUserId(), post.getPostId(),
                        " đã yêu thích bài viết của bạn", "like");
            }
            likeRepository.save(like);
            post.setLikeCount(post.getLikeCount() + 1);  // Tăng countLike
            postRepository.save(post);
        }
    }
//    @Override
//    public void deleteAllByPost(Post post) {
//        likeRepository.deleteAllByIdPost(post);
//    }
}
