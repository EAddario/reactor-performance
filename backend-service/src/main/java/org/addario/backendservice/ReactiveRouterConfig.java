package org.addario.backendservice;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@EnableAutoConfiguration
public class ReactiveRouterConfig {
    private final ReactiveRequestHandler requestHandler;

    public ReactiveRouterConfig(ReactiveRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> topLevelRouter() {
        return RouterFunctions.route()
                .path("functional", this::routerFunction)
                .build();
    }

    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/blocking/fibonacci/random", requestHandler::blockingRandomFibonacciHandler)
                .GET("/reactive/fibonacci/random", requestHandler::reactiveRandomFibonacciHandler)
                .GET("/blocking/fibonacci/{num}", requestHandler::blockingFibonacciHandler)
                .GET("/reactive/fibonacci/{num}", requestHandler::reactiveFibonacciHandler)
                .GET("/blocking/name/{num}", requestHandler::blockingNameHandler)
                .GET("/blocking/nameslist", requestHandler::blockingNamesListHandler)
                .GET("/reactive/name/{num}", requestHandler::reactiveNameHandler)
                .GET("/reactive/nameslist", requestHandler::reactiveNamesListHandler)
                .GET("/reactive/nameslist/stream", requestHandler::reactiveNamesListStreamHandler)
                .onError(Exception.class, (e, _) -> ServerResponse.badRequest().bodyValue("BackendService Error: " + e.getMessage()))
                .build();
    }
}
