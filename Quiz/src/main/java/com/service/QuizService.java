package com.service;

import com.entities.Answer;
import com.entities.Question;
import com.entities.Quiz;
import com.mapper.QuestionMapper;
import com.mapper.QuizMapper;
import com.model.QuestionInput;
import com.model.QuizInput;
import com.output.QuizJSON;
import com.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Transactional
    public List<QuizJSON> getAll() {
        return quizRepository.findAll().stream().map(QuizMapper::entityToJson).collect(Collectors.toList());
    }

    @Transactional
    public QuizJSON findByName(String name) {
        Quiz quiz = quizRepository.findByName(name).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Quiz with name " + name + " not found."));
        return QuizMapper.entityToJson(quiz);
    }

    @Transactional
    public Quiz findQuizByName(String name) {
        return quizRepository.findByName(name).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Quiz with name " + name + " not found."));
    }

    @Transactional
    public QuizJSON addQuiz(QuizInput input) {

        Quiz quiz = QuizMapper.quizToEntity(input);
        quiz.getQuestions().forEach(question -> {
            question.setQuiz(quiz);
            question.getAnswers().forEach(answer -> answer.setQuestion(question));
        });
        quizRepository.save(quiz);

        return QuizMapper.entityToJson(quiz);

    }
}
