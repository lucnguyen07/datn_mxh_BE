package edu.poly.mxh.DTO;

import edu.poly.mxh.Modal.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Long postId;
    private UserProfile users;
    private String content;
    private String postType;
    private LocalDateTime createAt;
    private boolean checkLiked;
}
