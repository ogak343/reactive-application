package com.example.reactiveapplication.route;

import com.example.reactiveapplication.dto.req.UserCreate;
import com.example.reactiveapplication.dto.resp.UserResp;
import com.example.reactiveapplication.handler.UserHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRoute {

    private final UserHandler handler;

    public UserRoute(UserHandler handler) {
        this.handler = handler;
    }

    @Bean("userRouter")
    @RouterOperations({
            @RouterOperation(
                    path = "/users/{id}",
                    method = RequestMethod.GET,
                    beanClass = UserHandler.class,
                    beanMethod = "get",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    operation = @Operation(
                            operationId = "getUserById",
                            summary = "Get user by ID",
                            parameters = {
                                    @Parameter(
                                            name = "id",
                                            description = "User ID",
                                            required = true,
                                            in = ParameterIn.PATH,
                                            schema = @Schema(type = "string")
                                    )
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "User found",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = UserResp.class)
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "User not found"
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/users",
                    method = RequestMethod.POST,
                    beanClass = UserHandler.class,
                    beanMethod = "create",
                    consumes = {MediaType.APPLICATION_JSON_VALUE},
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    operation = @Operation(
                            operationId = "createUser",
                            summary = "Create a new user",
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = UserCreate.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "User created",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = UserResp.class)
                                            )
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> userRouter() {
        return RouterFunctions.route()
                .GET("/users/{id}", handler::get)
                .POST("/users", handler::create)
                .build();
    }
}
