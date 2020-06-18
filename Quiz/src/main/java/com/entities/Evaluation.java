package com.entities;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "evaluation")
@Table(name = "evaluation")
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "score")
    private String score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student")
    private User student;

    @OneToMany(mappedBy = "evaluation", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<UserAnswer> userAnswers;

    public void addUserAnswer(UserAnswer userAnswer) {
        if (userAnswers == null) {
            userAnswers = new ArrayList<>();
        }
        userAnswers.add(userAnswer);
    }
}
