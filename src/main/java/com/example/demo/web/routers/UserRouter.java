package com.example.demo.web.routers;

import com.example.demo.web.service.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration(proxyBeanMethods = false)
public class UserRouter {

    @Bean
    private RouterFunction<ServerResponse> route(UserHandler userHandler) {
        return RouterFunctions
                .route(POST("/v1/users").and(accept(MediaType.APPLICATION_JSON)), userHandler::createUser)
                .andRoute(GET("/v1/users/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::getUser)
                .andRoute(GET("/v1/users").and(accept(MediaType.APPLICATION_JSON)), userHandler::getAllUsers)
                .andRoute(GET("/v1/streamUsers").and(accept(MediaType.TEXT_EVENT_STREAM)), userHandler::streamUsers)
                .andRoute(PUT("/v1/users/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::updateUser)
                .andRoute(DELETE("/v1/users/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::deleteUser);
    }
}
