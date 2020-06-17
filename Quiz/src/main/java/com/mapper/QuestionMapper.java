package com.mapper;

import com.entities.Answer;
import com.entities.Question;
import com.model.QuestionInput;
import com.output.QuestionJSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionMapper {

    public static QuestionJSON entityToJson(Question question) {
        return QuestionJSON.builder()
                .id(question.getId())
                .question(question.getQuestion())
                .answers(question.getAnswers().stream().map(Answer::getText).collect(Collectors.toList()))
                .correctAnswer(question.getCorrectAnswer())
                .quiz(question.getQuiz().getName())
                .build();
    }

    private static List<Answer> addAnswers(QuestionInput input) {

        char x = 'A';
        List<String> answers = input.getAnswers();
        List<Answer> result = new ArrayList<>();

        for (String answer : answers) {
            Answer answer1 = new Answer(x +":"+ answer);
            result.add(answer1);
            x += 1;
        }

        return result;
    }

    public static Question questionToEntity(QuestionInput input) {
        return Question.builder()
                .question(input.getQuestion())
                .correctAnswer(input.getCorrectAnswer())
                .answers(addAnswers(input))
                .build();
    }
}
