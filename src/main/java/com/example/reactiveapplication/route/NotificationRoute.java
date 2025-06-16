package com.example.reactiveapplication.route;

import com.example.reactiveapplication.dto.req.EventDto;
import com.example.reactiveapplication.dto.resp.ResponseDto;
import com.example.reactiveapplication.handler.NotificationHandler;
import io.swagger.v3.oas.annotations.Operation;
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
public class NotificationRoute {
    private final NotificationHandler handler;

    public NotificationRoute(NotificationHandler handler) {
        this.handler = handler;
    }

    @Bean("notificationRouter")
    @RouterOperations({
            @RouterOperation(
                    path = "/notifications/event",
                    method = RequestMethod.POST,
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    consumes = {MediaType.APPLICATION_JSON_VALUE},
                    beanClass = NotificationHandler.class,
                    beanMethod = "sendEvent",
                    operation = @Operation(
                            operationId = "sendEvent",
                            summary = "Send a notification event",
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = EventDto.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Notification sent successfully",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = ResponseDto.class)
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Invalid request",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = ResponseDto.class)
                                            )
                                    )
                            }
                    )
            )
    })

    public RouterFunction<ServerResponse> notificationRouter() {
        return RouterFunctions.route()
                .POST("/notifications/event", handler::sendEvent)
                .build();
    }
}
