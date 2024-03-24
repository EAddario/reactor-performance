package org.addario.reactiveservice;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Random;

@RestController
// http://localhost:8081/
public class ReactiveServiceController {
    @GetMapping(path="/long/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<ResponseEntity<String>> getLongFibonacci(@PathVariable Long num) {
        Mono<String> result = Mono.fromSupplier(() -> String.format("%,d", FibonacciLong.calculate(num)))
                //.log("getLongFibonacci", Level.INFO, SignalType.ON_SUBSCRIBE, SignalType.ON_NEXT)
                .subscribeOn(Schedulers.parallel());
        return result.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(path="/long/random", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<ResponseEntity<String>> getLongRandomFibonacci() {
        var rnd = new Random().nextLong(40);
        Mono<String> result = Mono.fromSupplier(() -> String.format("%,d", FibonacciLong.calculate(rnd)))
                //.log("getLongFibonacci", Level.INFO, SignalType.ON_SUBSCRIBE, SignalType.ON_NEXT)
                .subscribeOn(Schedulers.parallel());
        return result.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(path="/mono/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<ResponseEntity<String>> getMonoFibonacci(@PathVariable Long num) {
        Mono<String> result = FibonacciMono.calculate(num)
                .map(v -> String.format("%,d", v))
                //.log("getMonoFibonacci", Level.INFO, SignalType.ON_SUBSCRIBE, SignalType.ON_NEXT)
                .subscribeOn(Schedulers.parallel());
        return result.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(path="/mono/random", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<ResponseEntity<String>> getMonoRandomFibonacci() {
        var rnd = new Random().nextLong(40);
        Mono<String> result = FibonacciMono.calculate(rnd)
                .map(v -> String.format("%,d", v))
                //.log("getMonoFibonacci", Level.INFO, SignalType.ON_SUBSCRIBE, SignalType.ON_NEXT)
                .subscribeOn(Schedulers.parallel());
        return result.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(path="/name/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<ResponseEntity<String>> getName(@PathVariable int num) {
        num = num < 1 ? 1 : num;
        num = num > 1_000_000_000 ? 1_000_000_000 : num;

        return Names.getName(num).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
