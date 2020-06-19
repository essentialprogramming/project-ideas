package com.controller;


import com.entities.Question;
import com.entities.Quiz;
import com.model.QuizInput;
import com.output.EvaluationJSON;
import com.output.QuestionJSON;
import com.output.QuizJSON;
import com.service.QuizService;
import com.web.json.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class QuizController {

    @Autowired
    private QuizService service;

    @GET
    @Path("/quiz")
    @Produces(MediaType.APPLICATION_JSON)
    public List<QuizJSON> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/questions")
    @Produces(MediaType.APPLICATION_JSON)
    public List<QuestionJSON> getQuestionsByQuiz(@QueryParam("qId") int id) {

        List<QuestionJSON> questions = service.getQuestionsByQuizId(id);

        return questions;

    }

    @POST
    @Path("/quiz")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)

    public QuizJSON addQuiz(QuizInput input) {
        return service.addQuiz(input);
    }


    @PUT
    @Path("/quiz/user")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse assignQuizToUser(@QueryParam("qId") int quizId, @QueryParam("username") String username) {

        service.assignQuizToUser(quizId, username);

        return new JsonResponse()
                .with("status", "OK")
                .done();
    }

    @GET
    @Path("/quiz/score")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EvaluationJSON> getQuizListWithScore(@QueryParam("username") String username) {
        return service.getQuizListWithScores(username);
    }


}
