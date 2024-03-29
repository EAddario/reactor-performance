package org.addario.reactivegateway;

import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Log
@Service
public class ReactiveRequestHandler {
    public Mono<ServerResponse> blockingFibonacciHandler(ServerRequest serverRequest) {
        var num = Long.parseLong(serverRequest.pathVariable("num"));
        log.info(STR."blockingFibonacciHandler: calculating Fibonacci of \{num}");
        var responseMono = ReactiveRestConsumer.getBlockingFibonacci(num);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMono, String.class);
    }

    public Mono<ServerResponse> blockingRandomFibonacciHandler(ServerRequest serverRequest) {
        log.info("blockingRandomFibonacciHandler: calculating random Fibonacci");
        var responseMono = ReactiveRestConsumer.getBlockingRandomFibonacci();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMono, String.class);
    }

    public Mono<ServerResponse> reactiveFibonacciHandler(ServerRequest serverRequest) {
        var num = Long.parseLong(serverRequest.pathVariable("num"));
        log.info(STR."reactiveFibonacciHandler: calculating Fibonacci of \{num}");
        var responseMono = ReactiveRestConsumer.getReactiveFibonacci(num);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMono, String.class);
    }

    public Mono<ServerResponse> reactiveRandomFibonacciHandler(ServerRequest serverRequest) {
        log.info("reactiveRandomFibonacciHandler: calculating random Fibonacci");
        var responseMono = ReactiveRestConsumer.getReactiveRandomFibonacci();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMono, String.class);
    }

    public Mono<ServerResponse> blockingNameHandler(ServerRequest serverRequest) {
        var num = Integer.parseInt(serverRequest.pathVariable("num"));
        log.info(STR."blockingNameHandler: generating \{String.format("%,d", num)} random names");
        var responseMono = ReactiveRestConsumer.getBlockingName(num);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMono, String.class);
    }

    public Mono<ServerResponse> blockingNamesListHandler(ServerRequest serverRequest) {
        log.info("blockingNamesListHandler: retrieving list of names");
        var responseFlux = ReactiveRestConsumer.getBlockingNamesList();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseFlux, String.class);
    }

    public Mono<ServerResponse> reactiveNameHandler(ServerRequest serverRequest) {
        var num = Integer.parseInt(serverRequest.pathVariable("num"));
        log.info(STR."reactiveNameHandler: generating \{String.format("%,d", num)} random names");
        var responseMono = ReactiveRestConsumer.getReactiveName(num);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMono, String.class);
    }

    public Mono<ServerResponse> reactiveNamesListHandler(ServerRequest serverRequest) {
        log.info("reactiveNamesListHandler: retrieving list of names");
        var responseFlux = ReactiveRestConsumer.getReactiveNamesList();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseFlux, String.class);
    }

    public Mono<ServerResponse> reactiveNamesListStreamHandler(ServerRequest serverRequest) {
        log.info("reactiveNamesListStreamHandler: streaming list of names");
        var responseFlux = ReactiveRestConsumer.getReactiveNamesListStream();

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseFlux, String.class);
    }
}
