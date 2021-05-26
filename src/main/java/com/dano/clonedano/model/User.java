package com.dano.clonedano.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Data
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "userName")
})
public class User extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickName;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;


}
