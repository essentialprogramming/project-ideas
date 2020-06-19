package com.model;


import com.entities.Answer;
import com.entities.UserAnswer;
import lombok.*;

import java.util.List;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationInput {

    private int quizId;
    private List<AnswerInput> userAnswers;
    private String username;
}
