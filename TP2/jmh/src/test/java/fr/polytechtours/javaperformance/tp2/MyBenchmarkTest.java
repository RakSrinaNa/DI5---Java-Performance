package fr.polytechtours.javaperformance.tp2;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class MyBenchmarkTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {0, 1},
                {1, 1},
                {2, 2},
                {3, 6},
                {4, 24},
                {5, 120},
                {6, 720},
                {7, 5040},
                {12, 479001600}
        });
    }

    public MyBenchmarkTest(int input, int expected) {
        this.input = input;
        this.expected = expected;
    }

    @org.junit.Test
    public void testFact() {
        assertEquals(expected, MyBenchmark.fact(input));
    }

    @org.junit.Test
    public void testFactRec() {
        assertEquals(expected, MyBenchmark.factRec(input));
    }

    private int input;
    private int expected;

}