package com.config;


import com.controller.*;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import io.swagger.v3.jaxrs2.integration.resources.AcceptHeaderOpenApiResource;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.glassfish.jersey.server.ResourceConfig;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

import javax.ws.rs.ApplicationPath;

/**
 * JAX-RS application configuration class.
 */

@ApplicationPath("/api")

public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        register(QuizController.class);
        register(EvaluationController.class);

        register(JacksonJaxbJsonProvider.class);


        OpenAPI openAPI = new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Quiz API")
                        .description(
                                "Quiz application using  OpenAPI 3.")
                        .version("v1")
                )
                .schemaRequirement("Authorization", new SecurityScheme()
                        .name("Authorization")
                        .description("JWT Authorization header using the Bearer scheme. Example: \\\\\\\"Authorization: Bearer {token}\\\\\\\"")
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                );


        SwaggerConfiguration oasConfig = new SwaggerConfiguration()
                .openAPI(openAPI)
                .prettyPrint(true);

        AcceptHeaderOpenApiResource openApiResource = new AcceptHeaderOpenApiResource();
        openApiResource.setOpenApiConfiguration(oasConfig);
        register(openApiResource);


    }

}
