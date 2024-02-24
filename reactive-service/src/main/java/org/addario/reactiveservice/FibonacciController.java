package org.addario.reactiveservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Random;

@RestController
@RequestMapping("/fibonacci") // http://localhost:8080/fibonacci
public class FibonacciController {
    // http://localhost:8080/fibonacci
    @GetMapping(path="/{num}")
    public Mono<ResponseEntity<Long>> getFibonacci(@PathVariable Long num) {
        Mono<Long> result = Mono.just(Fibonacci.calculate(num));
        return result.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(path="/random")
    public Mono<ResponseEntity<Long>> getRandom() {
        Random rnd = new Random();
        Mono<Long> result = Mono.just(Fibonacci.calculate(rnd.nextLong(40)));
        return result.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
