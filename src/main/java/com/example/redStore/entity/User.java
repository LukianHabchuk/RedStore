package com.example.redStore.entity;

import com.example.redStore.enums.Role;
import com.example.redStore.enums.Status;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
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
