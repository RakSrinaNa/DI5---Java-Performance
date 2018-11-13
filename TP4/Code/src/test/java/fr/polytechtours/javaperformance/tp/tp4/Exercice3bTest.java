package fr.polytechtours.javaperformance.tp.tp4;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class Exercice3bTest {

    @Test
    public void testExercice3b() throws ExecutionException, InterruptedException {
        Assert.assertEquals(0, new Exercice3b().exercice3b(10, 500, 100).intValue());
        Assert.assertEquals(200, new Exercice3b().exercice3b(7, 5000, 600).intValue());
        Assert.assertEquals(100, new Exercice3b().exercice3b(4, 100000, 300).intValue());
        Assert.assertEquals(0, new Exercice3b().exercice3b(5, 5000000, 100000).intValue());
        Assert.assertEquals(40, new Exercice3b().exercice3b(100, 3000000, 260).intValue());
        Assert.assertEquals(2000, new Exercice3b().exercice3b(100, 5000000, 6000).intValue());
    }
}
