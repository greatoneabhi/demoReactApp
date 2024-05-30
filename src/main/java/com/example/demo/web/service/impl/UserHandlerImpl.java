package com.example.demo.web.service.impl;

import com.example.demo.web.records.User;
import com.example.demo.web.repositories.UserRepository;
import com.example.demo.web.service.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class UserHandlerImpl implements UserHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        Mono<User> userMono = serverRequest.bodyToMono(User.class);
        return userMono.flatMap(user -> userRepository.save(user))
                .flatMap(savedUser -> ServerResponse.created(URI.create("/v1/users/"+savedUser.id())).bodyValue(savedUser));
    }

    @Override
    public Mono<ServerResponse> getUser(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        Mono<User> userMono = userRepository.findById(id);
        return userMono.flatMap(user -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(user)).switchIfEmpty(ServerResponse.notFound().build()));
    }

    @Override
    public Mono<ServerResponse> getAllUsers(ServerRequest serverRequest) {
        Flux<User> allUsers = userRepository.findAll();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(allUsers, User.class);
    }

    @Override
    public Mono<ServerResponse> streamUsers(ServerRequest serverRequest) {
        Flux<User> allUsers = userRepository.findAll();
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(allUsers, User.class);
    }

    @Override
    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        Mono<User> userMono = serverRequest.bodyToMono(User.class);
        return userMono.flatMap(user -> {
            user.withId(id);
            return userRepository.save(user);
        }).flatMap(savedUser -> ServerResponse.ok().bodyValue(savedUser))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @Override
    public Mono<ServerResponse> deleteUser(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return userRepository.deleteById(id)
                .then(ServerResponse.noContent().build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
