package edu.poly.mxh.Service;

import edu.poly.mxh.Modal.Images;
import edu.poly.mxh.Modal.Post;

import java.util.List;

public interface ImagesService {
    void save(Images images);

    void remove(Long id);
    List<Images> findAllByPost(Post post);
}
