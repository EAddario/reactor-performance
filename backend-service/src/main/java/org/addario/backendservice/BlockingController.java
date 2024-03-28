package org.addario.backendservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/rest")
// http://localhost:8081/
public class BlockingController {
    @GetMapping(path="/blocking/fibonacci/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBlockingFibonacci(@PathVariable Long num) {
        var result = String.format("%,d", BlockingFibonacci.calculate(num));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path="/blocking/fibonacci/random", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBlockingRandomFibonacci() {
        var rnd = new Random().nextLong(40);
        var result = String.format("%,d", BlockingFibonacci.calculate(rnd));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path="/reactive/fibonacci/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<String>> getReactiveFibonacci(@PathVariable Long num) {
        var result = ReactiveFibonacci.calculate(num).map(v -> String.format("%,d", v));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path="/reactive/fibonacci/random", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<String>> getReactiveRandomFibonacci() {
        var rnd = new Random().nextLong(40);
        var result = ReactiveFibonacci.calculate(rnd).map(v -> String.format("%,d", v));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path="/blocking/name/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBlockingName(@PathVariable int num) {
        num = Math.max(num, 1);
        num = Math.min(num, 1_000_000_000);

        return new ResponseEntity<>(BlockingNames.getName(num), HttpStatus.OK);
    }

    @GetMapping(path="/blocking/nameslist", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getBlockingNamesList() {

        return new ResponseEntity<>(BlockingNames.getNamesList(), HttpStatus.OK);
    }

    @GetMapping(path="/reactive/name/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> getReactiveName(@PathVariable int num) {
        num = Math.max(num, 1);
        num = Math.min(num, 1_000_000_000);

        return ReactiveNames.getName(num).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(path="/reactive/nameslist", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<String>> getReactiveNamesList() {
        return new ResponseEntity<>(ReactiveNames.getNamesList(), HttpStatus.OK);
    }

    @GetMapping(path="/reactive/nameslist/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<String>> getReactiveNamesListStream() {
        return new ResponseEntity<>(ReactiveNames.getNamesList(), HttpStatus.OK);
    }
}
