package fr.polytechtours.javaperformance.tp.tp4;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class Exercice5Test {
    @Test
    public void testExercice5() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Assert.assertEquals("bob", Exercice5.getName(new Exercice5.Guy("bob")));
        Assert.assertEquals("dylan", Exercice5.getName(new Exercice5.Guy("dylan")));
        Assert.assertEquals("marley", Exercice5.getName(new Exercice5.Guy("marley")));
    }
}
