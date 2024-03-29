package org.addario.blockinggateway;

import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log
@RestController
public class BlockingController {
    @GetMapping(path="/blocking/fibonacci/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBlockingFibonacci(@PathVariable Long num) {
        log.info(STR."getBlockingFibonacci: calculating Fibonacci of \{num}");
        var response = BlockingRestConsumer.getBlockingFibonacci(num);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path="/blocking/fibonacci/random", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBlockingRandomFibonacci() {
        log.info("getBlockingRandomFibonacci: calculating random Fibonacci");
        var response = BlockingRestConsumer.getBlockingRandomFibonacci();

        return ResponseEntity.ok(response);
    }

    @GetMapping(path="/reactive/fibonacci/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getReactiveFibonacci(@PathVariable Long num) {
        log.info(STR."getReactiveFibonacci: calculating Fibonacci of \{num}");
        var response = BlockingRestConsumer.getReactiveFibonacci(num);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path="/reactive/fibonacci/random", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getReactiveRandomFibonacci() {
        log.info("getReactiveRandomFibonacci: calculating random Fibonacci");
        var response = BlockingRestConsumer.getReactiveRandomFibonacci();

        return ResponseEntity.ok(response);
    }

    @GetMapping(path="/blocking/name/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBlockingName(@PathVariable int num) {
        log.info(STR."getBlockingName: generating \{String.format("%,d", num)} random names");
        var response = BlockingRestConsumer.getBlockingName(num);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path="/blocking/nameslist", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getBlockingNamesList() {
        log.info("getBlockingNamesList: retrieving list of names");
        var response = BlockingRestConsumer.getBlockingNamesList();

        return ResponseEntity.ok(response);
    }

    @GetMapping(path="/reactive/name/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getReactiveName(@PathVariable int num) {
        log.info(STR."getReactiveName: generating \{String.format("%,d", num)} random names");
        var response = BlockingRestConsumer.getReactiveName(num);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path="/reactive/nameslist", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getReactiveNamesList() {
        log.info("getReactiveNamesList: retrieving list of names");
        var response = BlockingRestConsumer.getReactiveNamesList();

        return ResponseEntity.ok(response);
    }

    @GetMapping(path="/reactive/nameslist/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<String> getReactiveNamesListStream() {
        log.info("getReactiveNamesListStream: streaming list of names");

        return ResponseEntity.ok("Handling streaming events with Spring MVC is beyond the scope of this demo");
    }
}
