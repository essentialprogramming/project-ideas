package com.controller;

import com.model.UserInput;
import com.service.AuthenticationService;
import com.util.web.SessionUtils;
import com.web.json.JsonResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/")
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;

    @Context
    private HttpServletRequest httpRequest;

    @POST
    @Path("authenticate")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(hidden = true)
    public JsonResponse authenticate(@QueryParam("redirect_uri") String redirectUri, UserInput profileInput) {
        String username = profileInput.getUsername();
        String password = profileInput.getPassword();

        UserInput userInput = service.authenticate(username, password);

        if (userInput != null) {
            final String url = redirectUri;
            SessionUtils.setAttribute(httpRequest,"user", userInput);
            return new JsonResponse()
                    .with("status", "Redirect")
                    .with("redirectUrl", url).done();
        } else
            return new JsonResponse()
                    .with("status", "Error")
                    .with("error", "The username or password you entered is incorrect.").done();
    }

    @POST
    @Path("register")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonResponse register(UserInput profileInput) {
        service.register(profileInput);
        return new JsonResponse()
                .with("status", "OK")
                .done();
    }
}
