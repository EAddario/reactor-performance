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

        if (num > -1L && num < 41L) {
            log.info(STR."blockingFibonacciHandler: calculating Fibonacci of \{num}");

            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.fromCallable(() -> String.format("%,d", BlockingFibonacci.calculate(num))), String.class);
        } else {
            log.info(STR."blockingFibonacciHandler: \{num} is not between allowed range (0 and 40)");

            return ServerResponse.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(STR."\{num} is not between allowed range (0 and 40)"), String.class);
        }

    }

    public Mono<ServerResponse> blockingRandomFibonacciHandler(ServerRequest serverRequest) {
        var rnd = new Random().nextLong(40);
        log.info(STR."blockingRandomFibonacciHandler: calculating Fibonacci of \{rnd}");

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.fromCallable(() -> String.format("%,d", BlockingFibonacci.calculate(rnd))), String.class);
    }

    public Mono<ServerResponse> reactiveFibonacciHandler(ServerRequest serverRequest) {
        var num = Long.parseLong(serverRequest.pathVariable("num"));

        if (num > -1 && num < 41) {
            log.info(STR."reactiveFibonacciHandler: calculating Fibonacci of \{num}");

            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ReactiveFibonacci.calculate(num).map(v -> String.format("%,d", v)), String.class);
        } else {
            log.info(STR."reactiveFibonacciHandler: \{num} is not between allowed range (0 and 40)");

            return ServerResponse.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(STR."\{num} is not between allowed range (0 and 40)"), String.class);
        }

    }

    public Mono<ServerResponse> reactiveRandomFibonacciHandler(ServerRequest serverRequest) {
        var rnd = new Random().nextLong(38);
        log.info(STR."reactiveRandomFibonacciHandler: calculating Fibonacci of \{rnd}");

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ReactiveFibonacci.calculate(rnd).map(v -> String.format("%,d", v)), String.class);
    }

    public Mono<ServerResponse> blockingNameHandler(ServerRequest serverRequest) {
        var num = Integer.parseInt(serverRequest.pathVariable("num"));

        if (num > 0 && num < 1_000_001) {
            log.info(STR."blockingNameHandler: generating \{String.format("%,d", num)} random names");

            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.fromCallable(() -> BlockingNames.getName(num)), String.class);
        } else {
            log.info(STR."blockingNameHandler: \{num} is not between allowed range (1 and 1,000,000)");

            return ServerResponse.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(STR."\{num} is not between allowed range (1 and 1,000,000)"), String.class);
        }

    }

    public Mono<ServerResponse> blockingNamesListHandler(ServerRequest serverRequest) {
        log.info("blockingNamesListHandler: retrieving list of names");

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ReactiveNames.getNamesList().collectList(), ArrayList.class);
    }

    public Mono<ServerResponse> reactiveNameHandler(ServerRequest serverRequest) {
        var num = Integer.parseInt(serverRequest.pathVariable("num"));

        if (num > 0 && num < 1_000_001) {
            log.info(STR."reactiveNameHandler: generating \{String.format("%,d", num)} random names");

            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ReactiveNames.getName(num), String.class);
        } else {
            log.info(STR."reactiveNameHandler: \{num} is not between allowed range (1 and 1,000,000)");

            return ServerResponse.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(STR."\{num} is not between allowed range (1 and 1,000,000)"), String.class);
        }

    }

    public Mono<ServerResponse> reactiveNamesListHandler(ServerRequest serverRequest) {
        log.info("reactiveNamesListHandler: retrieving list of names");

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ReactiveNames.getNamesList().collectList(), ArrayList.class);
    }

    public Mono<ServerResponse> reactiveNamesListStreamHandler(ServerRequest serverRequest) {
        log.info("reactiveNamesListStreamHandler: streaming list of names");

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(ReactiveNames.getNamesList(), String.class);
    }
}
