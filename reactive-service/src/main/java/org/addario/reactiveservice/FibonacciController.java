package org.addario.reactiveservice;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Random;

@RestController
@RequestMapping("/fibonacci") // http://localhost:8081/fibonacci
public class FibonacciController {
    // http://localhost:8081/fibonacci
    @GetMapping(path="/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<ResponseEntity<String>> getFibonacci(@PathVariable Long num) {
        Mono<String> result = Mono.just(String.format("%,d", Fibonacci.calculate(num)));
        return result.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(path="/random", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<ResponseEntity<String>> getRandom() {
        Random rnd = new Random();
        Mono<String> result = Mono.just(String.format("%,d", Fibonacci.calculate(rnd.nextLong(40))));
        return result.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
