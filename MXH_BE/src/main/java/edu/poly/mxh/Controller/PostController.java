package edu.poly.mxh.Controller;

import edu.poly.mxh.Modal.*;
import edu.poly.mxh.DTO.PostDTO;
import edu.poly.mxh.Repository.LikeRepository;
import edu.poly.mxh.Repository.PostRepository;
import edu.poly.mxh.Repository.ReportRepository;
import edu.poly.mxh.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post")
@CrossOrigin("*")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ImagesService imagesService;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ReportService reportService;
    //Tạo bài post
    @PostMapping
    public ResponseEntity<Post> create(@RequestBody Post post) {
        postService.save(post);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }
    //Cập nhật post
    @PutMapping("/update")
    public ResponseEntity<?> updatePost(@RequestBody Post post) {
        Optional<Post> postOptional = postService.findById(post.getPostId());
        postService.update(post);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //Xóa tâất cả ảnh của bài post để update mới
    @PutMapping("/image")
    public ResponseEntity<?> updateImgPost(@RequestBody List<Long> deleteList){
        for (Long i: deleteList){
            imagesService.remove(i);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //image của bài post
    @PostMapping("/create/img")
    public ResponseEntity<?> createImg(@RequestBody Images[] imagePostList) {
        for (Images imagePost : imagePostList) {
            imagesService.save(imagePost);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    //Lấy tất cả các post cho bảng tin
//    @GetMapping("/{id}")
//    public ResponseEntity<List<PostDTO>> getAllPostNewFeed(@PathVariable Long id) {
//        //List<Users> users = iUserService.findFriendRequestsByIdAndStatusTrue(id);
//        List<PostDTO> postDisplays = new ArrayList<>();
//        List<Post> posts = new ArrayList<>();
//        posts.addAll(postService.findAllPersonalPost(userService.findById(id).get()));
//        // Thêm bài đăng công khai từ những người không phải là bạn bè
//        List<Post> publicPosts = postService.findAllPublicPosts();
//        for (Post publicPost : publicPosts) {
//            //if (!friends.contains(publicPost.getUser()) && !publicPost.getUser().equals(user)) {
//            posts.add(publicPost);
//            //}
//        }
//        if (posts.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        for (Post post : posts) {
//                PostDTO postDisplay = new PostDTO();
//                postDisplay.setPostId(post.getPostId());
//                postDisplay.setContent(post.getContent());
//                postDisplay.setPostType(post.getPostType());
//                postDisplay.setUsers(post.getUsers());
//                postDisplay.setCreateAt(post.getCratedAt());
//                postDisplays.add(postDisplay);
//        }
//        for (PostDTO p : postDisplays) {
//            if (likeRepository.existsByPost_PostIdAndUsers_UserId(p.getPostId(), id)){
//                p.setCheckLiked(true);
//            }
//        }
//        Collections.sort(postDisplays, Comparator.comparing(PostDTO::getCreateAt).reversed());
//        return new ResponseEntity<>(postDisplays, HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PostDTO>> getAllPostNewFeed(@PathVariable Long id) {
        List<PostDTO> postDisplays = new ArrayList<>();
        List<Post> posts = new ArrayList<>();
        Set<Long> postIds = new HashSet<>(); // Set để theo dõi các postId đã thêm

        // Thêm bài đăng cá nhân
        List<Post> personalPosts = postService.findAllPersonalPost(userService.findById(id).get());
        for (Post post : personalPosts) {
            if (postIds.add(post.getPostId())) { // Kiểm tra và thêm postId vào Set
                posts.add(post);
            }
        }

        // Thêm bài đăng công khai từ những người không phải là bạn bè
        List<Post> publicPosts = postService.findAllPublicPosts();
        for (Post publicPost : publicPosts) {
            if (postIds.add(publicPost.getPostId())) { // Kiểm tra và thêm postId vào Set
                posts.add(publicPost);
            }
        }

        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        for (Post post : posts) {
            PostDTO postDisplay = new PostDTO();
            postDisplay.setPostId(post.getPostId());
            postDisplay.setContent(post.getContent());
            postDisplay.setPostType(post.getPostType());
            postDisplay.setUsers(post.getUsers());
            postDisplay.setCreateAt(post.getCratedAt());
            postDisplays.add(postDisplay);
        }

        for (PostDTO p : postDisplays) {
            if (likeRepository.existsByPost_PostIdAndUsers_UserId(p.getPostId(), id)){
                p.setCheckLiked(true);
            }
        }

        Collections.sort(postDisplays, Comparator.comparing(PostDTO::getCreateAt).reversed());

        return new ResponseEntity<>(postDisplays, HttpStatus.OK);
    }


    //Lấy tất cả bài post theo id người dùng
    @GetMapping("/profile/{id}")
    public ResponseEntity<List<PostDTO>> getAllPostProfile(@PathVariable Long id) {
        List<PostDTO> postDisplays = new ArrayList<>();
        for (Post post : postService.findAllPersonalPost(userService.findById(id).get())) {
            PostDTO postDisplay = new PostDTO();
            postDisplay.setPostId(post.getPostId());
            postDisplay.setContent(post.getContent());
            postDisplay.setPostType(post.getPostType());
            postDisplay.setUsers(post.getUsers());
            postDisplay.setCreateAt(post.getCratedAt());
            postDisplays.add(postDisplay);
        }
        for (PostDTO p : postDisplays) {
            if (likeRepository.existsByPost_PostIdAndUsers_UserId(p.getPostId(), id)){
                p.setCheckLiked(true);
            }
        }
        Collections.reverse(postDisplays);
        return new ResponseEntity<>(postDisplays, HttpStatus.OK);
    }
    //Lấy tất cả image của các bài post
    @PostMapping("/image")
    public ResponseEntity<?> getImg(@RequestBody Post[] posts) {
        List<Object> objects = new ArrayList<>();
        for (Post p : posts) {
            List<Images> imagePosts = imagesService.findAllByPost(p);
            objects.add(imagePosts);
        }
        return new ResponseEntity<>(objects, HttpStatus.OK);
    }
    //Lấy tất cả ảnh của 1 bài post
    @GetMapping("/image/{id}")
    public ResponseEntity<List<Images>> getImagePost(@PathVariable Long id) {
        return new ResponseEntity<>(imagesService.findAllByPost(postService.findById(id).get()), HttpStatus.OK);
    }

    @PostMapping("/like")
    public ResponseEntity<List<Long>> getCountLikePost(@RequestBody Post[] post) {
        List<Long> list = new ArrayList<>();
        for (Post p : post) {
            list.add(likeService.countPostLike(p.getPostId()));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/get/like")
    public ResponseEntity<Long> getCountLikeOnePost(@RequestBody Post post) {
        return new ResponseEntity<>(likeService.countPostLike(post.getPostId()), HttpStatus.OK);
    }
    //Đếm số coment của mỗi bài post
    @PostMapping("/comment")
    public ResponseEntity<List<Long>> getCountCommentPost(@RequestBody Post[] posts) {
        List<Long> list = new ArrayList<>();
        for (Post p : posts) {
            list.add(commentService.countPostComment(p.getPostId()));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    //Lấy tất cả các comment của các bài post
    @PostMapping("/list/comment")
    public ResponseEntity<?> getListCommentAllPost(@RequestBody Post[] posts){
        List<Object> objects = new ArrayList<>();
        for (Post p : posts) {
            List<Comment> postCommentList = commentService.findAllByPost(p);
            objects.add(postCommentList);
        }
        return new ResponseEntity<>(objects, HttpStatus.OK);
    }

    //Lấy tất cả các reply comment của các bài post
    @PostMapping("/list/comment/replies")
    public ResponseEntity<List<Comment>> getRepliesForComments(@RequestBody List<Long> commentIds) {
        List<Comment> allReplies = new ArrayList<>();
        for (Long commentId : commentIds) {
            List<Comment> replies = commentService.getReplies(commentId);
            allReplies.addAll(replies);
        }
        return ResponseEntity.ok(allReplies);
    }

    @GetMapping("/interact/comment/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable Long id){
        Optional<Comment> postComment = commentService.findById(id);
        return postComment.map(comment -> new ResponseEntity<>(comment, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<List<Comment>> getAllPostComment(@PathVariable Long id) {
        List<Comment> postCommentList = commentService.findAllByPost(postService.findById(id).get());
        if (postCommentList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(postCommentList, HttpStatus.OK);
    }
    //Người dùng comment
    @PostMapping("/interact/comment")
    public ResponseEntity<?> comment(@RequestBody Comment postComment){
        commentService.save(postComment);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //Người dùng like or unlike post
    @PostMapping("/{postId}/likes/toggle/{userId}")
    public void toggleLike(@PathVariable Long postId, @PathVariable Long userId) {
        likeService.toggleLike(postId, userId);
    }
    //Lấy tất cả các post của bạn bè or ko phải bạn bè nhưng public
    @GetMapping("/wall/{id1}/{id2}")
    public ResponseEntity<List<PostDTO>> getAllPostWallFriend(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
        //id1 cua friend, id2 cua nguoi dang nhap
        List<PostDTO> postDisplays = new ArrayList<>();
//        Optional<Friends> friendRequest = iFriendService.findFriendRequest(id1, id2);
//        if (friendRequest.isPresent()) {
//            for (Posts post : iPostService.findAllFriendPost(id1)) {
//                transferPostDisplay(postDisplays, post);
//            }
//        } else {
            for (Post post : postService.findAllFriendPublicPost(id1)) {
                PostDTO postDisplay = new PostDTO();
                postDisplay.setPostId(post.getPostId());
                postDisplay.setContent(post.getContent());
                postDisplay.setPostType(post.getPostType());
                postDisplay.setUsers(post.getUsers());
                postDisplay.setCreateAt(post.getCratedAt());
                postDisplays.add(postDisplay);
            }
       // }
        for (PostDTO p : postDisplays) {
            if (likeRepository.existsByPost_PostIdAndUsers_UserId(p.getPostId(), id2)){
                p.setCheckLiked(true);
            }
        }
        Collections.reverse(postDisplays);
        return new ResponseEntity<>(postDisplays, HttpStatus.OK);
    }
    //Lấy tất cả báo cáo của tất cả bài viết
    @GetMapping("/report")
    public ResponseEntity<Iterable<Report>> showAllReportByAdmin() {
        Iterable<Report> reports = reportService.findAll();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }
    //Báo cáo bài viết
    @PostMapping("user/report")
    public ResponseEntity<?> AddReport(@RequestBody Report report) {
        report.setType("Chưa xử lý");
        report.setCreateAt(LocalDateTime.now());
        reportService.save(report);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @DeleteMapping("{id}")
//    public ResponseEntity<?> deletePost(@PathVariable Long id) {
//        postService.remove(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
