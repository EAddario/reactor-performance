package org.addario.blockingservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
// http://localhost:8080/
public class BlockingServiceController {
    @GetMapping(path="/fibonacci/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getFibonacci(@PathVariable Long num) {
        String result = String.format("%,d", Fibonacci.calculate(num));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path="/fibonacci/random", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getRandom() {
        Random rnd = new Random();
        String result = String.format("%,d", Fibonacci.calculate(rnd.nextLong(40)));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path="/name/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getName(@PathVariable int num) {
        num = num < 1 ? 1 : num;
        num = num > 1_000_000_000 ? 1_000_000_000 : num;
        String result = Names.getName(num);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
