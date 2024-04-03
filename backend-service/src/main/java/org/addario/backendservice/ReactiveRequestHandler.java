package org.addario.backendservice;

import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Random;

@Log
@Service
public class ReactiveRequestHandler {
    public Mono<ServerResponse> blockingFibonacciHandler(ServerRequest serverRequest) {
        var num = Long.parseLong(serverRequest.pathVariable("num"));
        log.info(STR."blockingFibonacciHandler: calculating Fibonacci of \{num}");
        var responseMono = Mono.just(String.format("%,d", BlockingFibonacci.calculate(num)));

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMono, String.class);
    }

    public Mono<ServerResponse> blockingRandomFibonacciHandler(ServerRequest serverRequest) {
        var rnd = new Random().nextLong(40);
        log.info(STR."blockingRandomFibonacciHandler: calculating Fibonacci of \{rnd}");
        var responseMono = Mono.just(String.format("%,d", BlockingFibonacci.calculate(rnd)));

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMono, String.class);
    }

    public Mono<ServerResponse> reactiveFibonacciHandler(ServerRequest serverRequest) {
        var num = Long.parseLong(serverRequest.pathVariable("num"));
        log.info(STR."reactiveFibonacciHandler: calculating Fibonacci of \{num}");
        var responseMono = ReactiveFibonacci.calculate(num).map(v -> String.format("%,d", v));

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMono, String.class);
    }

    public Mono<ServerResponse> reactiveRandomFibonacciHandler(ServerRequest serverRequest) {
        var rnd = new Random().nextLong(38);
        log.info(STR."reactiveRandomFibonacciHandler: calculating Fibonacci of \{rnd}");
        var responseMono = ReactiveFibonacci.calculate(rnd).map(v -> String.format("%,d", v));

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMono, String.class);
    }

    public Mono<ServerResponse> blockingNameHandler(ServerRequest serverRequest) {
        var num = Integer.parseInt(serverRequest.pathVariable("num"));
        num = Math.max(num, 1);
        num = Math.min(num, 1_000_000_000);
        log.info(STR."blockingNameHandler: generating \{String.format("%,d", num)} random names");
        var responseMono = Mono.just(BlockingNames.getName(num));

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMono, String.class);
    }

    public Mono<ServerResponse> blockingNamesListHandler(ServerRequest serverRequest) {
        log.info("blockingNamesListHandler: retrieving list of names");
        var responseMono = ReactiveNames.getNamesList().collectList();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMono, ArrayList.class);
    }

    public Mono<ServerResponse> reactiveNameHandler(ServerRequest serverRequest) {
        var num = Integer.parseInt(serverRequest.pathVariable("num"));
        num = Math.max(num, 1);
        num = Math.min(num, 1_000_000_000);
        log.info(STR."reactiveNameHandler: generating \{String.format("%,d", num)} random names");
        var responseMono = ReactiveNames.getName(num);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMono, String.class);
    }

    public Mono<ServerResponse> reactiveNamesListHandler(ServerRequest serverRequest) {
        log.info("reactiveNamesListHandler: retrieving list of names");
        var responseMono = ReactiveNames.getNamesList().collectList();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMono, ArrayList.class);
    }

    public Mono<ServerResponse> reactiveNamesListStreamHandler(ServerRequest serverRequest) {
        log.info("reactiveNamesListStreamHandler: streaming list of names");
        var responseFlux = ReactiveNames.getNamesList();

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseFlux, String.class);
    }
}
