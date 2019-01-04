package fr.polytechtours.javaperformance.tp.tp4;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

/**
 * Cette exercice permet de multiplier une matrice de int en entrée avec la matrice MATRIX_A et de retourner le résultat.
 */
public class Exercice1 {

    private static final Float[][] MATRIX_A = {
            {1/42f,1/42f,2/42f,2/42f,2/42f,1/42f,1/42f},
            {1/42f,2/42f,3/42f,4/42f,3/42f,2/42f,1/42f},
            {2/42f,3/42f,4/42f,5/42f,4/42f,3/42f,2/42f},
            {2/42f,4/42f,5/42f,8/42f,5/42f,4/42f,2/42f},
            {2/42f,3/42f,4/42f,5/42f,4/42f,3/42f,2/42f},
            {1/42f,2/42f,3/42f,4/42f,3/42f,2/42f,1/42f},
            {1/42f,1/42f,2/42f,2/42f,2/42f,1/42f,1/42f}
    };

    private static final float[][] MATRIX_B = {
            {1/42f,1/42f,2/42f,2/42f,2/42f,1/42f,1/42f},
            {1/42f,2/42f,3/42f,4/42f,3/42f,2/42f,1/42f},
            {2/42f,3/42f,4/42f,5/42f,4/42f,3/42f,2/42f},
            {2/42f,4/42f,5/42f,8/42f,5/42f,4/42f,2/42f},
            {2/42f,3/42f,4/42f,5/42f,4/42f,3/42f,2/42f},
            {1/42f,2/42f,3/42f,4/42f,3/42f,2/42f,1/42f},
            {1/42f,1/42f,2/42f,2/42f,2/42f,1/42f,1/42f}
    };

    public static float[][] multiply(final int[][] matrix) {
        final float[][] result = new float[7][7];

        for (Integer i = 0; i < 7; i = i + 1) {
            for (Integer j = 0; j < 7; j = j + 1) {
                Float currentValue = 0F;

                for (Integer k = 0; k < 7; k = k + 1) {
                    currentValue = currentValue + matrix[i][k] * MATRIX_A[k][j];
                }

                result[i][j] = currentValue;
            }
        }

        return result;
    }

    public static float[][] multiplyA(final int[][] matrix) {
        final float[][] result = new float[7][7];

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                float currentValue = 0F;

                for (int k = 0; k < 7; k++) {
                    currentValue += matrix[i][k] * MATRIX_B[k][j];
                }

                result[i][j] = currentValue;
            }
        }

        return result;
    }








    @State(Scope.Thread)
    public static class MyState {
        @Param({"0", "1"})
        public int i;
        final int[][] matrix = {
                {1, 1, 2, 2, 2, 1, 1},
                {1, 2, 3, 4, 3, 2, 1},
                {2, 3, 4, 5, 4, 3, 2},
                {2, 4, 5, 8, 5, 4, 2},
                {2, 3, 4, 5, 4, 3, 2},
                {1, 2, 3, 4, 3, 2, 1},
                {1, 1, 2, 2, 2, 1, 1}
        };
        final int[][] matrix2 = {
                {3, 1, 2, 2, 2, 1, 3},
                {1, 5, 3, 1, 3, 2, 1},
                {2, 3, 4, 5, 4, 3, 2},
                {2, 1, 5, 1, 5, 1, 2},
                {2, 3, 4, 5, 4, 3, 2},
                {1, 3, 3, 1, 3, 2, 1},
                {3, 1, 2, 2, 2, 1, 3}
        };
    }

    @Benchmark
    @Warmup(iterations = 2, time = 1, batchSize = 3)
    @Measurement (iterations = 2, time = 1, batchSize = 3)
    public void test(MyState state, Blackhole bh) {
        bh.consume(multiply(state.i == 0?state.matrix:state.matrix2));
    }

    @Benchmark
    @Warmup (iterations = 2, time = 1, batchSize = 3)
    @Measurement (iterations = 2, time = 1, batchSize = 3)
    public void testA(MyState state, Blackhole bh) {
        bh.consume(multiplyA(state.i == 0?state.matrix:state.matrix2));
    }


}
