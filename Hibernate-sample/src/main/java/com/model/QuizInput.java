package com.model;


import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizInput {

    private String name;
    private String level;
    private List<QuestionInput> questions;

}
