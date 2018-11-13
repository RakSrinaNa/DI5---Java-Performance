package fr.polytechtours.javaperformance.tp.tp4;

import org.junit.Assert;
import org.junit.Test;

public class Exercice1Test {
    
    @Test
    public void test1() {
        final int[][] matrix = {
                {1, 1, 2, 2, 2, 1, 1},
                {1, 2, 3, 4, 3, 2, 1},
                {2, 3, 4, 5, 4, 3, 2},
                {2, 4, 5, 8, 5, 4, 2},
                {2, 3, 4, 5, 4, 3, 2},
                {1, 2, 3, 4, 3, 2, 1},
                {1, 1, 2, 2, 2, 1, 1}
        };

        final float[][] multiply = Exercice1.multiply(matrix);

        Assert.assertEquals(7, multiply.length);
        Assert.assertArrayEquals(new float[]{0.3809524f, 0.61904764f, 0.85714287f, 1.1428572f, 0.85714287f, 0.61904764f, 0.3809524f}, multiply[0], 0.000001F);
        Assert.assertArrayEquals(new float[]{0.61904764f, 1.0476191f, 1.4285715f, 1.9523809f, 1.4285715f, 1.0476191f, 0.61904764f}, multiply[1], 0.000001F);
        Assert.assertArrayEquals(new float[]{0.85714287f, 1.4285715f, 1.9761904f, 2.6666667f, 1.9761904f, 1.4285715f, 0.85714287f}, multiply[2], 0.000001F);
        Assert.assertArrayEquals(new float[]{1.1428572f, 1.9523809f, 2.6666667f, 3.6666667f, 2.6666667f, 1.9523809f, 1.1428572f}, multiply[3], 0.000001F);
        Assert.assertArrayEquals(new float[]{0.85714287f, 1.4285715f, 1.9761904f, 2.6666667f, 1.9761904f, 1.4285715f, 0.85714287f}, multiply[4], 0.000001F);
        Assert.assertArrayEquals(new float[]{0.61904764f, 1.0476191f, 1.4285715f, 1.9523809f, 1.4285715f, 1.0476191f, 0.61904764f}, multiply[5], 0.000001F);
        Assert.assertArrayEquals(new float[]{0.3809524f, 0.61904764f, 0.85714287f, 1.1428572f, 0.85714287f, 0.61904764f, 0.3809524f}, multiply[6], 0.000001F);
    }

    @Test
    public void test2() {
        final int[][] matrix = {
                {3, 1, 2, 2, 2, 1, 3},
                {1, 5, 3, 1, 3, 2, 1},
                {2, 3, 4, 5, 4, 3, 2},
                {2, 1, 5, 1, 5, 1, 2},
                {2, 3, 4, 5, 4, 3, 2},
                {1, 3, 3, 1, 3, 2, 1},
                {3, 1, 2, 2, 2, 1, 3}
        };

        final float[][] multiply = Exercice1.multiply(matrix);

        Assert.assertEquals(7, multiply.length);
        Assert.assertArrayEquals(new float[]{0.47619048f, 0.71428573f, 1.0476191f, 1.3333334f, 1.0476191f, 0.71428573f, 0.47619048f}, multiply[0], 0.000001F);
        Assert.assertArrayEquals(new float[]{0.54761904f, 0.9047619f, 1.2857143f, 1.6666666f, 1.2857143f, 0.9047619f, 0.54761904f}, multiply[1], 0.000001F);
        Assert.assertArrayEquals(new float[]{0.85714287f, 1.4285715f, 1.9761904f, 2.6666667f, 1.9761904f, 1.4285715f, 0.85714287f}, multiply[2], 0.000001F);
        Assert.assertArrayEquals(new float[]{0.6666667f, 1.0f, 1.4047619f, 1.7619047f, 1.4047619f, 1.0f, 0.6666667f}, multiply[3], 0.000001F);
        Assert.assertArrayEquals(new float[]{0.85714287f, 1.4285715f, 1.9761904f, 2.6666667f, 1.9761904f, 1.4285715f, 0.85714287f}, multiply[4], 0.000001F);
        Assert.assertArrayEquals(new float[]{0.5f, 0.8095238f, 1.1428572f, 1.4761904f, 1.1428572f, 0.8095238f, 0.5f}, multiply[5], 0.000001F);
        Assert.assertArrayEquals(new float[]{0.47619048f, 0.71428573f, 1.0476191f, 1.3333334f, 1.0476191f, 0.71428573f, 0.47619048f}, multiply[6], 0.000001F);
    }
}
