package fr.polytechtours.javaperformance.tp2;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

public class MyBenchmark2 {

    @State(Scope.Thread)
    public static class MyState {
        @Param ({"50", "50_000"})
        public int i;
    }

    @Benchmark
    @Warmup (iterations = 2, time = 8, batchSize = 3)
    @Measurement (iterations = 2, time = 8, batchSize = 3)
    public void testConcat(MyBenchmark.MyState state, Blackhole bh) {
        bh.consume(stringConcat(state.i));
    }

    @Benchmark
    @Warmup (iterations = 2, time = 8, batchSize = 3)
    @Measurement (iterations = 2, time = 8, batchSize = 3)
    public void testBuilder(MyBenchmark.MyState state, Blackhole bh) {
        bh.consume(stringBuilder(state.i));
    }

    @Benchmark
    @Warmup (iterations = 2, time = 8, batchSize = 3)
    @Measurement (iterations = 2, time = 8, batchSize = 3)
    public void testBuffer(MyBenchmark.MyState state, Blackhole bh) {
        bh.consume(stringBuffer(state.i));
    }



    public String stringConcat(int max) {
        String string = "";

        for (int i = 0; i < max; i++)
            string = string + i;

        return string;
    }

    public String stringBuilder(int max) {
        StringBuilder string = new StringBuilder(100);

        for (int i = 0; i < max; i++)
            string.append(i);

        return string.toString();
    }

    public String stringBuffer(int max) {
        StringBuffer string = new StringBuffer();

        for (int i = 0; i < max; i++)
            string.append(i);

        return string.toString();
    }

}
