package fr.polytechtours.javaperformance.tp2;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.stream.Stream;

public class MyBenchmark2 {
    @State(Scope.Thread)
    public static class MyState {
        @Param ({"50", "50000"})
        public int i;
    }

    @Benchmark
    @Warmup (iterations = 2, time = 1, batchSize = 3)
    @Measurement (iterations = 2, time = 1, batchSize = 3)
    public void testConcat(MyBenchmark2.MyState state, Blackhole bh) {
        bh.consume(stringConcat(2, state.i));
    }

    @Benchmark
    @Warmup (iterations = 2, time = 1, batchSize = 3)
    @Measurement (iterations = 2, time = 1, batchSize = 3)
    public void testBuilder(MyBenchmark2.MyState state, Blackhole bh) {
        bh.consume(stringBuilder(2, state.i));
    }

    @Benchmark
    @Warmup (iterations = 2, time = 1, batchSize = 3)
    @Measurement (iterations = 2, time = 1, batchSize = 3)
    public void testBuffer(MyBenchmark2.MyState state, Blackhole bh) {
        bh.consume(stringBuffer(2, state.i));
    }

    public static String stringConcat(Object cat, int max) {
        String string = "";

        for (int i = 0; i < max; i++)
            string = string + cat;

        return string;
    }

    public static String stringBuilder(Object cat, int max) {
        StringBuilder string = new StringBuilder(500);

        for (int i = 0; i < max; i++)
            string.append(cat);

        return string.toString();
    }

    public static String stringBuffer(Object cat, int max) {
        StringBuffer string = new StringBuffer(500);

        for (int i = 0; i < max; i++)
            string.append(cat);

        return string.toString();
    }
}
