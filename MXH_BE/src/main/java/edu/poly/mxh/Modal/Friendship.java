package edu.poly.mxh.Modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "friendship")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendship_id")
    private Long friendshipId;

    @Column(name = "sender_type")
    private String senderType;

    @Column(name = "user_id_sender")
    private Long userIdSender;

    @Column(name = "recipient_type")
    private String recipientType;

    @Column(name = "user_id_recipient")
    private Long userIdRecipient;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "status")
    private Integer status;
}
