package edu.poly.mxh.Service;

import edu.poly.mxh.Modal.Images;
import edu.poly.mxh.Modal.Post;
import edu.poly.mxh.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ImagesServiceImpl implements ImagesService{
    @Autowired
    private ImageRepository imageRepository;

    @Override
    @Transactional
    public void save(Images images) {
        images.setCreateAt(LocalDateTime.now());
        imageRepository.save(images);
    }

    @Override
    public void remove(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public List<Images> findAllByPost(Post post) {
        return imageRepository.findAllByPost(post);
    }
}
