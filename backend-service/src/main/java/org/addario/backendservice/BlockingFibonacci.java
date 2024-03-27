package org.addario.backendservice;

public class BlockingFibonacci {
    //Highly inefficient Fibonacci algorithm used to simulate service load
    public static Long calculate(Long num) {
        if (num == 1) return 1L;
        if (num < 1) return 0L;

        return calculate(num - 1L) + calculate(num - 2L);
    }
}
