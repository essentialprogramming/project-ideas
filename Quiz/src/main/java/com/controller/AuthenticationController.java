package com.controller;

import com.model.UserInput;
import com.output.UserJSON;
import com.service.AuthenticationService;
import com.util.web.SessionUtils;
import com.web.json.JsonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Authorization resource", tags = { "Authorization", },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Redirect URL if user successfully authenticated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JsonResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JsonResponse.class))),
            })
    public JsonResponse authenticate(@QueryParam("redirect_uri") String redirectUri, UserInput profileInput) {
        String username = profileInput.getUsername();

        UserJSON userJSON = service.authenticate(username);

        if (userJSON != null) {
            final String url = redirectUri;
            SessionUtils.setAttribute(httpRequest,"user", userJSON);
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
    @Operation(summary = "Authorization resource", tags = { "Authorization", },
            responses = {
                    @ApiResponse(responseCode = "200", description = "User successfully registered",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JsonResponse.class))),
            })
    public JsonResponse register(UserInput profileInput) {
        service.register(profileInput);
        return new JsonResponse()
                .with("status", "OK")
                .done();
    }
}
