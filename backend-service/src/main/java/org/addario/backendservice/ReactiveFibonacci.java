package org.addario.backendservice;

import reactor.core.publisher.Mono;

public class ReactiveFibonacci {
    //Highly inefficient Fibonacci algorithm used to simulate service load
    public static Mono<Long> calculate(Long num) {
        return fibonacciNumber(num);
    }

    private static Mono<Long> fibonacciNumber(Long num) {
        if (num == 1) return Mono.just(1L);
        if (num < 1) return Mono.just(0L);

        return Mono.zip(fibonacciNumber(num - 1), fibonacciNumber(num - 2), Long::sum);
    }
}
