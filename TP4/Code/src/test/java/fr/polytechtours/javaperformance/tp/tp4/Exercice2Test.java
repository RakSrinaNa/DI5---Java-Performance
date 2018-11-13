package fr.polytechtours.javaperformance.tp.tp4;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class Exercice2Test {

    @Test
    public void testFibonacci() {
        Assert.assertEquals(701408733, Exercice2.fibonacci(43));
    }
    @Test
    public void testFibonacciA() {
        Assert.assertEquals(701408733, Exercice2.fibonacciA(43, new HashMap<>()));
    }
}
