//package edu.poly.mxh.Repository.RepositoryImpl;
//
//import edu.poly.mxh.Modal.Comment;
//import edu.poly.mxh.Repository.CommentRepository;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.Query;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class CommentRepositoryImpl implements CommentRepository {
//    @Autowired
//    private EntityManager entityManager;
//
//    @Override
//    public List<Comment> findAllByPost(Long postId) {
//        Query query = entityManager.createNativeQuery("SELECT * FROM comment WHERE post_id = ?1", Comment.class);
//        query.setParameter(1, postId);
//        return query.getResultList();
//    }
//}
