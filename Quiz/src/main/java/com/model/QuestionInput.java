package com.model;

import lombok.*;

import java.util.List;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionInput {

    private String question;
    private String correctAnswer;
    private List<String> answers;

}
