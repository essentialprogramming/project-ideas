package com.entities;


import lombok.*;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDate date;

    @ManyToMany(mappedBy = "quizList", fetch = FetchType.EAGER)
    private List<User> students;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Question> questions;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
    private List<Evaluation> evaluations;

    public void addStudent(User user) {
        if (students == null) {
            students = new ArrayList<>();
            students.add(user);
        }
        else if(!students.contains(user)){
            students.add(user);
        }
    }


}

