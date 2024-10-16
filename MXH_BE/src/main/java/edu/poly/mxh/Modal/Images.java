package edu.poly.mxh.Modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Images_id")
    private Long imagesId;

    @Column(name = "url")
    private String url;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDate updateAt;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
