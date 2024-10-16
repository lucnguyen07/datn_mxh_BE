package edu.poly.mxh.Service;

import edu.poly.mxh.Modal.Post;
import edu.poly.mxh.Modal.UserProfile;

import java.util.List;
import java.util.Optional;

public interface PostService {
    void save(Post post);

    boolean update(Post posts);
    List<Post> findAllPersonalPost(UserProfile users);

    List<Post> findAllFriendPublicPost(Long id);

    Optional<Post> findById(Long id);

    List<Post> findAllPublicPosts();
}
