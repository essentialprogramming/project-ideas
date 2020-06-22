package com.controller;

import com.model.EvaluationInput;
import com.service.EvaluationService;
import com.web.json.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class EvaluationController {

    @Autowired
    private EvaluationService service;

    @POST
    @Path("/evaluation")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse submitEvaluation(EvaluationInput input) {

        service.addEvaluation(input);

        return new JsonResponse()
                .with("status", "OK")
                .done();
    }
}
