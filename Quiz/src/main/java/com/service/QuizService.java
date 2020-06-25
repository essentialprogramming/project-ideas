package com.service;

import com.entities.*;
import com.mapper.EvaluationMapper;
import com.mapper.QuestionMapper;
import com.mapper.QuizMapper;
import com.model.QuizInput;
import com.output.EvaluationJSON;
import com.output.QuestionJSON;
import com.output.QuizJSON;
import com.repository.QuizRepository;
import com.repository.UserRepository;
import com.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private QuizRepository quizRepository;
    private UserRepository userRepository;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    public QuizService(QuizRepository quizRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<QuizJSON> getAll(String username) {

        String sql = "select new com.entities.Quiz(q.id, q.name) from quiz q inner join q.students t where t.username = :username";
        EntityManager entityManager = emf.createEntityManager();

        Query query = entityManager.createQuery(sql);
        query.setParameter("username", username);

        List<Quiz> quizList = query.getResultList();

        if (quizList != null) {
            return quizList.stream().map(QuizMapper::entityToJson).collect(Collectors.toList());
        } else return new ArrayList<>();
    }

    @Transactional
    public QuizJSON addQuiz(QuizInput input) {

        Quiz quiz = QuizMapper.quizToEntity(input);
        quiz.getQuestions().values().forEach(question -> {
            question.setQuiz(quiz);
            question.getAnswers().forEach(answer -> answer.setQuestion(question));
        });
        quiz.setDate(String.valueOf(LocalDate.now()));
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
        List<QuestionJSON> questions = quiz.getQuestions().values().stream().map(QuestionMapper::entityToJson).collect(Collectors.toList());
        return questions;
    }

    @Transactional
    public void assignQuizToUser(int quizId, String username) {
        Quiz quiz = findById(quizId);
        User user = userRepository.findById(username).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User " + username + " not found."));

        quiz.addStudent(user);
        user.addQuiz(quiz);
    }

    @Transactional
    public List<EvaluationJSON> getQuizListWithScores(String username) {

        User user = userRepository.findById(username).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "User " + username + " not found."));
        List<Evaluation> result = new ArrayList<>();

        List<Quiz> quizList = user.getQuizList();

        for (Quiz quiz : quizList) {
            if (quiz.getEvaluations() != null) {
                quiz.getEvaluations().forEach(evaluation -> {
                    if (!evaluation.getScore().isEmpty() && evaluation.getStudent().equals(user)) {
                        result.add(evaluation);
                    }
                });
            } else return new ArrayList<>();
        }

        return result.stream().map(EvaluationMapper::entityToJson).collect(Collectors.toList());
    }

    @Transactional
    public List<QuizJSON> getQuizList(String username, String date, int year, String groupNumber) {

        List<Quiz> quizList = quizRepository.findAll();

        List<Quiz> result = quizList.stream()
                .filter(quiz -> (username == null) || quiz.getStudents().stream().anyMatch(student -> student.getUsername().equals(username)))
                .filter(quiz -> (date == null) || DateUtil.isLater(quiz.getDate(), date))
                .filter(quiz -> (year == 0) || quiz.getStudents() != null && quiz.getStudents().stream().anyMatch(student -> student.getYear() == year))
                .filter(quiz -> (groupNumber == null) || quiz.getStudents().stream().anyMatch(student -> student.getGroup().equals(groupNumber)))
                .collect(Collectors.toList());

        return result.stream().map(QuizMapper::entityToJson).collect(Collectors.toList());
    }
}
