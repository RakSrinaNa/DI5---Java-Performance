package fr.polytechtours.javaperformance.tp.tp4;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;

/**
 * Cet exercice est exactement le même que précédemment, sauf qu'une opération modulo est appliqué au compteur lors de chaque incrémentation.
 */
@State(Scope.Benchmark)
public class Exercice3b {

    private static final Object MUTEX = new Object();

    private Integer count = 0;
    private int countA = 0;
    private AtomicInteger countB = new AtomicInteger(0);

    private synchronized void incrementCounter(final Integer modulo) {
        synchronized (MUTEX) {
            count = (count + 1) % modulo;
        }
    }

    private void incrementCounterA(final Integer modulo) {
        synchronized (MUTEX) {
            countA = (countA + 1) % modulo;
        }
    }

    private void iterate(final Integer nbIteration, final Integer modulo) {
        for(Integer i = 0; i < nbIteration; i++) {
            this.incrementCounter(modulo);
        }
    }


    private void iterateA(final int nbIteration, int modulo) {
        for (int i = 0; i < nbIteration; i++) {
            this.incrementCounterA(modulo);
        }
    }

    private void iterateB(final int nbIteration, int modulo) {
        for (int i = 0; i < nbIteration; i++) {
            countB.accumulateAndGet(1, (left, right) -> (left + right) % modulo);
        }
    }

    public Integer exercice3b(final Integer nbThreads, final Integer nbIterationByThread, final Integer modulo) throws ExecutionException, InterruptedException {
        final ExecutorService service = Executors.newFixedThreadPool(nbThreads);
        final List<Future<Runnable>> futures = new ArrayList<>();

        for (Integer i = 0; i < nbThreads; i++) {
            final Future future = service.submit(() -> iterate(nbIterationByThread, modulo));
            futures.add(future);
        }

        // Wait for it...
        for (final Future<Runnable> future : futures) {
            future.get();
        }

        return count;
    }


    public int exercice3bA(final int nbThreads, final int nbIterationByThread, final int modulo) throws ExecutionException, InterruptedException {
        final ExecutorService service = Executors.newFixedThreadPool(nbThreads);
        final List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < nbThreads; i++) {
            futures.add(service.submit(() -> iterateA(nbIterationByThread, modulo)));
        }
        service.shutdown();

        // Wait for it...
        for (final Future<?> future : futures) {
            future.get();
        }

        return countA;
    }

    public int exercice3bB(final int nbThreads, final int nbIterationByThread, final int modulo) throws ExecutionException, InterruptedException {
        final ExecutorService service = Executors.newFixedThreadPool(nbThreads);
        final List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < nbThreads; i++) {
            futures.add(service.submit(() -> iterateB(nbIterationByThread, modulo)));
        }
        service.shutdown();

        for (Future<?> future : futures) {
            future.get();
        }

        return countB.get();
    }

    //@Benchmark
    @Warmup(iterations = 2, time = 1, batchSize = 3)
    @Measurement(iterations = 2, time = 10, batchSize = 3)
    public void test(Blackhole bh) throws ExecutionException, InterruptedException {
        bh.consume(exercice3b(i, j, k).intValue());
    }

    @Benchmark
    @Warmup(iterations = 2, time = 1, batchSize = 3)
    @Measurement(iterations = 2, time = 10, batchSize = 3)
    public void testA(Blackhole bh) throws ExecutionException, InterruptedException {
        bh.consume(exercice3bA(i, j, k));
    }

    @Benchmark
    @Warmup(iterations = 2, time = 1, batchSize = 3)
    @Measurement(iterations = 2, time = 10, batchSize = 3)
    public void testB(Blackhole bh) throws ExecutionException, InterruptedException {
        bh.consume(exercice3bB(i, j, k));
    }

    @Param({"10", "100"})
    private int i;

    @Param({"10", "10000"})
    private int j;

    @Param({"10", "10000"})
    private int k;
}
