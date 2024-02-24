package org.addario.blockingservice;

public class Fibonacci {
    //Highly inefficient Fibonacci algorithm used to simulate service load
    public static Long calculate(Long num) {

        if (num == 1) return 1L;
        if (num < 1) return 0L;

        return calculate(num - 1L) + calculate(num - 2L);
    }
}
