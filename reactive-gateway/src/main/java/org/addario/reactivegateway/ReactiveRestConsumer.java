package org.addario.reactivegateway;

import lombok.extern.java.Log;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log
public class ReactiveRestConsumer {
    private static final WebClient webClient = webClient();

    private static WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080/functional")
                .build();
    }

    public static Mono<String> getBlockingFibonacci(Long num) {
        return webClient.get()
                .uri("/blocking/fibonacci/{num}", num)
                .retrieve()
                .bodyToMono(String.class);
    }

    public static Mono<String> getBlockingRandomFibonacci() {
        return webClient.get()
                .uri("/blocking/fibonacci/random")
                .retrieve()
                .bodyToMono(String.class);
    }

    public static Mono<String> getReactiveFibonacci(Long num) {
        return webClient.get()
                .uri("/reactive/fibonacci/{num}", num)
                .retrieve()
                .bodyToMono(String.class);
    }

    public static Mono<String> getReactiveRandomFibonacci() {
        return webClient.get()
                .uri("/reactive/fibonacci/random")
                .retrieve()
                .bodyToMono(String.class);
    }

    public static Mono<String> getBlockingName(int num) {
        return webClient.get()
                .uri("/blocking/name/{num}", num)
                .retrieve()
                .bodyToMono(String.class);
    }

    public static Flux<String> getBlockingNamesList() {
        return webClient.get()
                .uri("/blocking/nameslist")
                .retrieve()
                .bodyToFlux(String.class);
    }

    public static Mono<String> getReactiveName(int num) {
        return webClient.get()
                .uri("/reactive/name/{num}", num)
                .retrieve()
                .bodyToMono(String.class);
    }

    public static Flux<String> getReactiveNamesList() {
        return webClient.get()
                .uri("/reactive/nameslist")
                .retrieve()
                .bodyToFlux(String.class);
    }

    public static Flux<String> getReactiveNamesListStream() {
        return webClient.get()
                .uri("/reactive/nameslist/stream")
                .retrieve()
                .bodyToFlux(String.class);
    }
}
