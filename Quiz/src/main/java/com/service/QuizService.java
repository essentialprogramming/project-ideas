package com.service;

import com.entities.*;
import com.mapper.EvaluationMapper;
import com.mapper.QuestionMapper;
import com.mapper.QuizMapper;
import com.model.QuestionInput;
import com.model.QuizInput;
import com.output.EvaluationJSON;
import com.output.QuestionJSON;
import com.output.QuizJSON;
import com.repository.QuizRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private QuizRepository quizRepository;
    private UserRepository userRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

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

    @Transactional
    public Quiz findById(int id) {
        return quizRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Quiz with id " + id + " not found."));
    }

    @Transactional
    public List<QuestionJSON> getQuestionsByQuizId(int id) {
        Quiz quiz = findById(id);
        List<QuestionJSON> questions = quiz.getQuestions().stream().map(QuestionMapper::entityToJson).collect(Collectors.toList());
        return questions;
    }

    @Transactional
    public void assignQuizToUser(int quizId, String username) {
        Quiz quiz = findById(quizId);
        User user = userRepository.findById(username).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User " + username + " not found."));

        user.addQuiz(quiz);
    }

    @Transactional
    public List<EvaluationJSON> getQuizListWithScores(String username) {

        User user = userRepository.findById(username).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User " + username + " not found."));
        List<Evaluation> result = new ArrayList<>();

        List<Quiz> quizList = user.getQuizList();
        quizList.forEach(quiz -> {
            if (quiz.getEvaluations() != null) {
                quiz.getEvaluations().forEach(evaluation -> {
                    if (!evaluation.getScore().isEmpty() && evaluation.getStudent().equals(user)) {
                        result.add(evaluation);
                    }
                });
            }
        });

        return result.stream().map(EvaluationMapper::entityToJson).collect(Collectors.toList());
    }
}
