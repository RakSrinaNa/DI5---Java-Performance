package fr.polytechtours.javaperformance.tp.tp4;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class Exercice3aTest {

    @Test
    public void testExercice3a() throws ExecutionException, InterruptedException {
        Assert.assertEquals(5000, new Exercice3a().exercice3a(10, 500).intValue());
        Assert.assertEquals(35000, new Exercice3a().exercice3a(7, 5000).intValue());
        Assert.assertEquals(200000, new Exercice3a().exercice3a(2, 100000).intValue());
        Assert.assertEquals(25000000, new Exercice3a().exercice3a(5, 5000000).intValue());
        Assert.assertEquals(300000000, new Exercice3a().exercice3a(100, 3000000).intValue());
        Assert.assertEquals(500000000, new Exercice3a().exercice3a(100, 5000000).intValue());
    }
}
