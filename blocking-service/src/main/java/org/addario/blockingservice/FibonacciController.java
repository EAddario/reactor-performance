package org.addario.blockingservice;

import org.springframework.http.HttpStatus;
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
    @GetMapping(path="/{num}")
    public ResponseEntity<Long> getFibonacci(@PathVariable Long num) {
        return new ResponseEntity<>(Fibonacci.calculate(num), HttpStatus.OK);
    }

    @GetMapping(path="/random")
    public ResponseEntity<Long> getRandom() {
        return new ResponseEntity<>(Fibonacci.calculate(new Random().nextLong(40)), HttpStatus.OK);
    }
}
