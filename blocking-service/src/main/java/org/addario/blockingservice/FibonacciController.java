package org.addario.blockingservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/fibonacci") // http://localhost:8080/fibonacci
public class FibonacciController {
    // http://localhost:8080/fibonacci
    @GetMapping(path="/long/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getFibonacci(@PathVariable Long num) {
        String result = String.format("%,d", Fibonacci.calculate(num));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path="/long/random", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getRandom() {
        Random rnd = new Random();
        String result = String.format("%,d", Fibonacci.calculate(rnd.nextLong(40)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
