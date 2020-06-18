package com.service;


import com.entities.*;
import com.mapper.EvaluationMapper;
import com.model.AnswerInput;
import com.model.EvaluationInput;
import com.output.EvaluationJSON;
import com.repository.EvaluationRepository;
import com.repository.QuestionRepository;
import com.repository.QuizRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluationService {


    private EvaluationRepository evaluationRepository;
    private QuizRepository quizRepository;
    private UserRepository userRepository;
    private QuestionRepository questionRepository;

    @Autowired
    public EvaluationService(EvaluationRepository evaluationRepository, QuizRepository quizRepository, UserRepository userRepository, QuestionRepository questionRepository) {
        this.evaluationRepository = evaluationRepository;
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    public EvaluationJSON addEvaluation(EvaluationInput input) {

        Evaluation evaluation = EvaluationMapper.evaluationToEntity(input);

        Quiz quiz = quizRepository.findById(input.getQuizId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Quiz with id " + input.getQuizId() + " not found."));
        evaluation.setQuiz(quiz);

        for (AnswerInput answerInput : input.getUserAnswers()) {
            Question question = questionRepository.findById(answerInput.getQuestionId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Question with id " + answerInput.getQuestionId() + " not found."));
            UserAnswer userAnswer = new UserAnswer(answerInput.getAnswer(), question);
            userAnswer.setQuestion(question);
            userAnswer.setEvaluation(evaluation);
            evaluation.addUserAnswer(userAnswer);
        }

        evaluationRepository.save(evaluation);

        return EvaluationMapper.entityToJson(evaluation);
    }
}
