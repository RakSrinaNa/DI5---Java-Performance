package fr.polytechtours.javaperformance.tp.tp4;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.HashMap;
import java.util.Map;

/**
 * Cette exercice met en place un algorithme permettant de calculer le 43eme facteur de la suite de Fibonacci.
 */
public class Exercice2 {

    public static int fibonacci(final Integer i) {
        if(i < 0) {
            throw new IllegalArgumentException("Invalid input value");
        }

        return (i < 3) ? i : fibonacci(i - 1) + fibonacci(i - 2);
    }


    public static int fibonacciA(final int i, Map<Integer, Integer> cache) {
        if(i < 0) {
            throw new IllegalArgumentException("Invalid input value");
        }

        return (i < 3) ? i : cache.computeIfAbsent(i, ii -> fibonacciA(i - 1, cache) + fibonacciA(i - 2, cache));
    }

    public static int fibonacciB() {
        return 701408733;
    }

    @State(Scope.Benchmark)
    public static class MyState {
        @Param({"43"})
        public int i;
        public Map<Integer, Integer> cache = new HashMap<>();
    }

    @Benchmark
    @Warmup(iterations = 2, time = 1, batchSize = 3)
    @Measurement (iterations = 2, time = 5, batchSize = 3)
    public void test(MyState state, Blackhole bh) {
        bh.consume(fibonacci(state.i));
    }

    @Benchmark
    @Warmup (iterations = 2, time = 1, batchSize = 3)
    @Measurement (iterations = 2, time = 5, batchSize = 3)
    public void testA(MyState state, Blackhole bh) {
        bh.consume(fibonacciA(state.i, state.cache));
    }


    @Benchmark
    @Warmup (iterations = 2, time = 1, batchSize = 3)
    @Measurement (iterations = 2, time = 5, batchSize = 3)
    public void testB(Blackhole bh) {
        bh.consume(fibonacciB());
    }

}
