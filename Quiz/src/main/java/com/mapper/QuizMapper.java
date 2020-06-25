package com.mapper;

import com.entities.Question;
import com.entities.Quiz;
import com.entities.User;
import com.model.QuizInput;
import com.output.QuizJSON;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class QuizMapper {

    public static QuizJSON entityToJson(Quiz quiz) {
        return QuizJSON.builder()
                .id(quiz.getId())
                .name(quiz.getName())
                .level(quiz.getLevel())
                .date(String.valueOf(quiz.getDate()))
                .students(quiz.getStudents() != null ? quiz.getStudents().stream().map(User::getUsername).collect(Collectors.toList()) : new ArrayList<>())
                .questions(quiz.getQuestions() != null ? quiz.getQuestions().values().stream().map(Question::getQuestion).collect(Collectors.toList()) : new ArrayList<>())
                .build();

    }

    public static Quiz quizToEntity(QuizInput input) {
        return Quiz.builder()
                .name(input.getName())
                .level(input.getLevel())
                .questions(input.getQuestions().stream().map(QuestionMapper::questionToEntity).collect(Collectors.toMap(Question::getId, question -> question)))
                .build();
    }
}
