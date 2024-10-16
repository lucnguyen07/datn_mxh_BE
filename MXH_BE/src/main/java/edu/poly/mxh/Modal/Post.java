package edu.poly.mxh.Modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "content")
    private String content;

    @Column(name = "post_type")
    private String postType;

    @Column(name = "crated_at")
    private LocalDateTime cratedAt;

    @Column(name = "update_at")
    private LocalDate updateAt;

    @Column(name = "like_count")
    private Long likeCount;

    @Column(name = "coment_count")
    private Long comentCount;

    @Column(name = "share_count")
    private Long shareCount;

    @Column(name = "rep_cmt_id")
    private Integer repCmtId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserProfile users;
}
