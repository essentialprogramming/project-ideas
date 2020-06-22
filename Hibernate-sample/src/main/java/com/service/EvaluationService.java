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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;


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

    @Transactional
    public EvaluationJSON addEvaluation(EvaluationInput input) {

        Evaluation evaluation = EvaluationMapper.evaluationToEntity(input);
        User user = userRepository.findById(input.getUsername()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User " + input.getUsername() + " not found."));

        Quiz quiz = quizRepository.findById(input.getQuizId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Quiz with id " + input.getQuizId() + " not found."));
        evaluation.setQuiz(quiz);
        evaluation.setStudent(user);

        for (AnswerInput answerInput : input.getUserAnswers()) {
            Question question = questionRepository.findById(answerInput.getQuestionId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Question with id " + answerInput.getQuestionId() + " not found."));
            UserAnswer userAnswer = new UserAnswer(answerInput.getAnswer(), question);
            userAnswer.setQuestion(question);
            userAnswer.setEvaluation(evaluation);
            evaluation.addUserAnswer(userAnswer);
        }

        evaluation.setScore(compute(input));
        evaluationRepository.save(evaluation);

        return EvaluationMapper.entityToJson(evaluation);
    }

    @Transactional
    public String compute(EvaluationInput input) {

        int correctAnswers = 0;
        Quiz quiz = quizRepository.findById(input.getQuizId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Quiz with id " + input.getQuizId() + " not found."));

        for (AnswerInput answerInput : input.getUserAnswers()) {

            Question question = questionRepository.findById(answerInput.getQuestionId()).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Question with id " + answerInput.getQuestionId() + " not found."));

            if (question.getCorrectAnswer().equals(answerInput.getAnswer())) {
                correctAnswers++;
            }
        }

        String result = correctAnswers + " of " + quiz.getQuestions().size();
        return result;

    }
}
