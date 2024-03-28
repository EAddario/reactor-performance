package org.addario.backendservice;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;

@Service
public class ReactiveRequestHandler {
    public Mono<ServerResponse> blockingFibonacciHandler(ServerRequest serverRequest) {
        var num = Long.parseLong(serverRequest.pathVariable("num"));
        var responseMono = Mono.just(String.format("%,d", BlockingFibonacci.calculate(num)));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMono, String.class);
    }

    public Mono<ServerResponse> blockingRandomFibonacciHandler(ServerRequest serverRequest) {
        var rnd = new Random().nextLong(40);
        var responseMono = Mono.just(String.format("%,d", BlockingFibonacci.calculate(rnd)));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMono, String.class);
    }

    public Mono<ServerResponse> reactiveFibonacciHandler(ServerRequest serverRequest) {
        var num = Long.parseLong(serverRequest.pathVariable("num"));
        var responseMono = ReactiveFibonacci.calculate(num).map(v -> String.format("%,d", v));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMono, String.class);
    }

    public Mono<ServerResponse> reactiveRandomFibonacciHandler(ServerRequest serverRequest) {
        var rnd = new Random().nextLong(40);
        var responseMono = ReactiveFibonacci.calculate(rnd).map(v -> String.format("%,d", v));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMono, String.class);
    }

    public Mono<ServerResponse> blockingNameHandler(ServerRequest serverRequest) {
        var num = Integer.parseInt(serverRequest.pathVariable("num"));
        num = Math.max(num, 1);
        num = Math.min(num, 1_000_000_000);
        var responseMono = Mono.just(BlockingNames.getName(num));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMono, String.class);
    }

    public Mono<ServerResponse> blockingNamesListHandler(ServerRequest serverRequest) {
        var responseFlux = Flux.fromIterable(BlockingNames.getNamesList());
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseFlux, String.class);
    }

    public Mono<ServerResponse> reactiveNameHandler(ServerRequest serverRequest) {
        var num = Integer.parseInt(serverRequest.pathVariable("num"));
        num = Math.max(num, 1);
        num = Math.min(num, 1_000_000_000);
        var responseMono = ReactiveNames.getName(num);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseMono, String.class);
    }

    public Mono<ServerResponse> reactiveNamesListHandler(ServerRequest serverRequest) {
        var responseFlux = ReactiveNames.getNamesList();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseFlux, String.class);
    }

    public Mono<ServerResponse> reactiveNamesListStreamHandler(ServerRequest serverRequest) {
        var responseFlux = ReactiveNames.getNamesList();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseFlux, String.class);
    }
}
