package com.config;


import com.controller.AuthenticationController;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import io.swagger.v3.jaxrs2.integration.resources.AcceptHeaderOpenApiResource;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * JAX-RS application configuration class.
 */

@ApplicationPath("api/auth")

public class AuthorizationApplicationConfig extends ResourceConfig {

    public AuthorizationApplicationConfig() {
        register(AuthenticationController.class);
        register(JacksonJaxbJsonProvider.class);

        OpenAPI openAPI = new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Auth API")
                        .description(
                                "Authentication API using OpenAPI 3.")
                        .version("v1")
                );


        SwaggerConfiguration oasConfig = new SwaggerConfiguration()
                .openAPI(openAPI)
                .prettyPrint(true);

        AcceptHeaderOpenApiResource openApiResource = new AcceptHeaderOpenApiResource();
        openApiResource.setOpenApiConfiguration(oasConfig);
        register(openApiResource);


    }

}
