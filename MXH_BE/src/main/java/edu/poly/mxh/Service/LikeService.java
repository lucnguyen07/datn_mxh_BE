package edu.poly.mxh.Service;

import edu.poly.mxh.Modal.Post;

public interface LikeService {
    Long countPostLike(Long postId);
    void toggleLike(Long postId, Long userId);

    //void deleteAllByPost(Post post);
}
