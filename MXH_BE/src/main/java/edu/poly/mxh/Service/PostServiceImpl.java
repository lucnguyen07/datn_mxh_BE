package edu.poly.mxh.Service;

import edu.poly.mxh.Modal.Post;
import edu.poly.mxh.Modal.UserProfile;
import edu.poly.mxh.Repository.CommentRepository;
import edu.poly.mxh.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LikeService likeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ImagesService imagesService;

    @Override
    @Transactional
    public void save(Post post) {
        post.setCratedAt(LocalDateTime.now());
        post.setPostType("Công khai");
        post.setLikeCount(0L);
        post.setComentCount(0L);
        post.setShareCount(0L);
        postRepository.save(post);
    }

    @Override
    @Transactional
    public boolean update(Post posts) {
        Optional<Post> postOptional = postRepository.findById(posts.getPostId());
        if(!postOptional.isPresent()){
            return false;
        }
        if(posts.getContent() != null){
            postOptional.get().setContent(posts.getContent());
        }
//        if(posts.getPostType() != null) {
//            postOptional.get().setPostType(posts.getPostType());
//        }
        postRepository.save(postOptional.get());
        return true;
    }

//    @Override
//    @Transactional
//    public void remove(Long id) {
//        likeService.deleteAllByPost(findById(id).get());
//        commentService.deleteAllByPost(findById(id).get());
//        imagesService.deleteAllByPosts(findById(id).get());
//        postRepository.deleteById(id);
//    }

    @Override
    public List<Post> findAllPersonalPost(UserProfile users) {
        return postRepository.findAllByUsers(users);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> findAllPublicPosts() {
        return postRepository.findByPostType("Công khai");
    }

    @Override
    public List<Post> findAllFriendPublicPost(Long id) {
        return postRepository.findAllFriendPublicPost(id);
    }
}
