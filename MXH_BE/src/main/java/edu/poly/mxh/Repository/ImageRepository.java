package edu.poly.mxh.Repository;

import edu.poly.mxh.Modal.Images;
import edu.poly.mxh.Modal.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Images, Long> {
    List<Images> findAllByPost(Post post);
}
