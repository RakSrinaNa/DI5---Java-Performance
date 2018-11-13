package fr.polytechtours.javaperformance.tp.tp4;

import org.junit.Assert;
import org.junit.Test;

public class Exercice2Test {

    @Test
    public void testFibonacci() {
        Assert.assertEquals(701408733, Exercice2.fibonacci(43));
    }
}
