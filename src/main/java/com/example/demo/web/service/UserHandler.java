package com.example.demo.web.service;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface UserHandler {

    Mono<ServerResponse> createUser(ServerRequest serverRequest);

    Mono<ServerResponse> getUser(ServerRequest serverRequest);

    Mono<ServerResponse> getAllUsers(ServerRequest serverRequest);

    Mono<ServerResponse> streamUsers(ServerRequest serverRequest);

    Mono<ServerResponse> updateUser(ServerRequest serverRequest);

    Mono<ServerResponse> deleteUser(ServerRequest serverRequest);
}
