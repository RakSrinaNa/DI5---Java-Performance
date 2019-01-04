package fr.polytechtours.javaperformance.tp.tp4;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;

/**
 * Cet exercice permet de générer un nombre de threads nbThreads. Chacun de ces threads va incrémenter un même et unique compteur commun à tous en parallèle un nombre nbIterationByThread de fois.
 * Une fois que chaque thread a terminé, la valeur finale de ce compteur unique est retournée.
 */
@State(Scope.Thread)
public class Exercice3a {

    private static final Object MUTEX = new Object();
    private Integer count = 0;
    private int countA = 0;
    private AtomicInteger countB = new AtomicInteger(0);

    private synchronized void incrementCounter() {
        synchronized (MUTEX) {
            count++;
        }
    }

    private void incrementCounterA() {
        synchronized (MUTEX) {
            countA++;
        }
    }

    private void iterate(final int nbIteration) {
        for (Integer i = 0; i < nbIteration; i++) {
            this.incrementCounter();
        }
    }

    private void iterateA(final int nbIteration) {
        for (int i = 0; i < nbIteration; i++) {
            this.incrementCounterA();
        }
    }

    private void iterateB(final int nbIteration) {
        for (int i = 0; i < nbIteration; i++) {
            countB.incrementAndGet();
        }
    }

    public Integer exercice3a(final Integer nbThreads, final Integer nbIterationByThread) throws ExecutionException, InterruptedException {
        final ExecutorService service = Executors.newFixedThreadPool(nbThreads);
        final List<Future<Runnable>> futures = new ArrayList<>();

        for (Integer i = 0; i < nbThreads; i++) {
            final Future future = service.submit(() -> iterate(nbIterationByThread));
            futures.add(future);
        }

        // Wait for it...
        for (final Future<Runnable> future : futures) {
            future.get();
        }

        return count;
    }

    public int exercice3aA(final int nbThreads, final int nbIterationByThread) throws ExecutionException, InterruptedException {
        final ExecutorService service = Executors.newFixedThreadPool(nbThreads);
        final List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < nbThreads; i++) {
            futures.add(service.submit(() -> iterateA(nbIterationByThread)));
        }
        service.shutdown();

        // Wait for it...
        for (final Future<?> future : futures) {
            future.get();
        }

        return countA;
    }

    public int exercice3aB(final int nbThreads, final int nbIterationByThread) throws ExecutionException, InterruptedException {
        final ExecutorService service = Executors.newFixedThreadPool(nbThreads);
        final List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < nbThreads; i++) {
            futures.add(service.submit(() -> iterateB(nbIterationByThread)));
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
        bh.consume(exercice3a(i, j).intValue());
    }

    @Benchmark
    @Warmup(iterations = 2, time = 1, batchSize = 3)
    @Measurement(iterations = 2, time = 10, batchSize = 3)
    public void testA(Blackhole bh) throws ExecutionException, InterruptedException {
        bh.consume(exercice3aA(i, j));
    }

    @Benchmark
    @Warmup(iterations = 2, time = 1, batchSize = 3)
    @Measurement(iterations = 2, time = 10, batchSize = 3)
    public void testB(Blackhole bh) throws ExecutionException, InterruptedException {
        bh.consume(exercice3aB(i, j));
    }

    @Param({"10", "100"})
    private int i;

    @Param({"10", "10000"})
    private int j;

}
