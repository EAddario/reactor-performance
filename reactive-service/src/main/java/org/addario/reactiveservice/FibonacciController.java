package org.addario.reactiveservice;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Random;

@RestController
@RequestMapping("/fibonacci") // http://localhost:8081/fibonacci
public class FibonacciController {
    // http://localhost:8081/fibonacci
    @GetMapping(path="/long/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<ResponseEntity<String>> getBlockingFibonacci(@PathVariable Long num) {
        Mono<String> result = Mono.fromSupplier(() -> String.format("%,d", FibonacciLong.calculate(num)))
                .subscribeOn(Schedulers.boundedElastic());
        return result.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(path="/long/random", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<ResponseEntity<String>> getRandom() {
        var rnd = new Random().nextLong(40);
        Mono<String> result = Mono.fromSupplier(() -> String.format("%,d", FibonacciLong.calculate(rnd)))
                .subscribeOn(Schedulers.boundedElastic());
        return result.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(path="/mono/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<ResponseEntity<String>> getNonBlockingFibonacci(@PathVariable Long num) {
        Mono<String> result = FibonacciMono.calculate(num)
                .map(v -> String.format("%,d", v))
                .subscribeOn(Schedulers.boundedElastic());
        return result.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(path="/mono/random", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<ResponseEntity<String>> getNonBlockingRandom() {
        var rnd = new Random().nextLong(40);
        Mono<String> result = FibonacciMono.calculate(rnd)
                .map(v -> String.format("%,d", v))
                .subscribeOn(Schedulers.boundedElastic());
        return result.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
