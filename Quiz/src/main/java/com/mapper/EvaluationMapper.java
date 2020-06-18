package com.mapper;

import com.entities.Evaluation;
import com.entities.UserAnswer;
import com.model.EvaluationInput;
import com.output.EvaluationJSON;

import java.util.stream.Collectors;

public class EvaluationMapper {

    public static Evaluation evaluationToEntity(EvaluationInput input) {

        return Evaluation.builder()
//                .score(input.getScore())
                .build();
    }

    public static EvaluationJSON entityToJson(Evaluation evaluation) {

        return EvaluationJSON.builder()
                .id(evaluation.getId())
                .score(evaluation.getScore())
                .quiz(evaluation.getQuiz().getId())
                .student(evaluation.getStudent() != null ? evaluation.getStudent().getUsername() : null)
                .userAnswers(evaluation.getUserAnswers().stream().map(UserAnswer::getText).collect(Collectors.toList()))
                .build();
    }
}
