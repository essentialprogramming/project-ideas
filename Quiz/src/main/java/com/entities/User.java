package com.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
@Table(name = "user")
public class User {

    @Id
    @Email
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_quiz",
            joinColumns = {@JoinColumn(name = "quiz_id")},
            inverseJoinColumns = {@JoinColumn(name = "username")})
    private List<Quiz> quizList;
}
