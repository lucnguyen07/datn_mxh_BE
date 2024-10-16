package edu.poly.mxh.Modal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "background")
    private String background;

    @Column(name = "create_at")
    private java.sql.Date createAt;

    @Column(name = "update_at")
    private java.sql.Date updateAt;

    @Column(name = "is_admin")
    private Integer isAdmin;

    @Column(name = "status")
    private String status;

    @Column(name = "is_online")
    private Integer isOnline;

    @Column(name = "otp")
    private Integer otp;

    @Column(name = "is_block")
    private Integer isBlock;
}
