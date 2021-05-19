package com.example.redstore.entity;

import com.example.redstore.enums.Role;
import com.example.redstore.enums.Status;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String userName;
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Enumerated(value = EnumType.STRING)
    private Status status;

    public User(String userName, String email, String password, Role role, Status status) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
    }
}
