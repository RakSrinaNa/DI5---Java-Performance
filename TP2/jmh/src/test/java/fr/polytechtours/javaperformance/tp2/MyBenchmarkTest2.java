package fr.polytechtours.javaperformance.tp2;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class MyBenchmarkTest2 {

    private final int count;

    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {"", 0},
                {"+", 1},
                {"++++++", 6},
                {"++++++++++++++++++++++++", 24},
                {"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++", 120}
        });
    }

    public MyBenchmarkTest2(String expected, int count) {
        this.expected = expected;
        this.count = count;
    }

    @org.junit.Test
    public void testConcat() {
        assertEquals(expected, MyBenchmark2.stringConcat("+", count));
    }

    @org.junit.Test
    public void testBuffer() {
        assertEquals(expected, MyBenchmark2.stringBuffer("+", count));
    }

    @org.junit.Test
    public void testBuilder() {
        assertEquals(expected, MyBenchmark2.stringBuilder("+", count));
    }

    private String expected;

}