package com.entities;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "quiz")
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "level")
    private String level;

    @Column(name = "date")
    private String date;

    @ManyToMany(mappedBy = "quizList", fetch = FetchType.LAZY)
    private List<User> students;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @MapKey(name = "id")
    private Map<Integer, Question> questions;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
    private List<Evaluation> evaluations;

    public void addStudent(User user) {
        if (students == null) {
            students = new ArrayList<>();
            students.add(user);
        } else if (!students.contains(user)) {
            students.add(user);
        }
    }


}

