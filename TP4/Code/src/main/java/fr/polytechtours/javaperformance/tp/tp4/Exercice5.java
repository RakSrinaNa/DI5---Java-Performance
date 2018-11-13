package fr.polytechtours.javaperformance.tp.tp4;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * L4objectif de cet exercice est de retourner la valeur du nom de l'objet Guy passé en paramètre.
 */
public class Exercice5 {

    public static final class Guy {
        private final String name;

        public Guy(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static String getName(final Guy guy) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (String) guy.getClass().getMethod("getName").invoke(guy);
    }

    public static String getNameA(final Guy guy) {
        return guy.getName();
    }



    @State(Scope.Benchmark)
    public static class MyState {
        @Param({"Thomas", "Clement", "Louis"})
        public String i;
    }

    @Benchmark
    @Warmup(iterations = 2, time = 1, batchSize = 3)
    @Measurement (iterations = 2, time = 1, batchSize = 3)
    public void test(MyState state, Blackhole bh) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        bh.consume(getName(new Guy(state.i)));
    }

    @Benchmark
    @Warmup (iterations = 2, time = 1, batchSize = 3)
    @Measurement (iterations = 2, time = 1, batchSize = 3)
    public void testA(MyState state, Blackhole bh) {
        bh.consume(getNameA(new Guy(state.i)));
    }

}
