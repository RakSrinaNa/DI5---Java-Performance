package fr.polytechtours.javaperformance.tp.tp4;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.xml.bind.DatatypeConverter.parseHexBinary;

/**
 * L'objectif de cet exercice est de condenser un nombre indéfini de taleaux de bytes dans un seul et unique tableau. Les tableaux seront concaténés dans l'ordre d'entrée.
 */
public class Exercice4 {

    public static byte[] exercice4(final byte[]... bytes) {
        List<Byte> list = new LinkedList<>();

        for(final byte[] byteArray : bytes) {
            for(final Byte currentByte : byteArray) {
                list.add(currentByte);
            }
        }

        final byte[] result = new byte[list.size()];

        for(Integer i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    public static Byte[] exercice4A(final byte[]... bytes) {
        List<Byte> list = new LinkedList<>();

        for(final byte[] byteArray : bytes) {
            list.addAll(Stream.of(byteArray).flatMap(e -> {
                Byte[] pp = new Byte[e.length];
                int i = 0;
                for(byte b : e)
                    pp[i++] = b;
                return Arrays.stream(pp);
            }).collect(Collectors.toList()));
        }

        final Byte[] result = new Byte[list.size()];

        return list.toArray(result);
    }

    public static byte[] exercice4B(final byte[]... bytes) {
        int size = 0;
        for(final byte[] byteArray : bytes) {
            size += byteArray.length;
        }

        final byte[] result = new byte[size];

        int i = 0;
        for(final byte[] byteArray : bytes) {
            for(final byte currentByte : byteArray) {
                result[i++] = currentByte;
            }
        }

        return result;
    }

    public static Byte[] exercice4C(final byte[]... bytes) {
        return Arrays.stream(bytes).flatMap(e -> {
                Byte[] pp = new Byte[e.length];
                int i = 0;
                for(byte b : e)
                    pp[i++] = b;
                return Arrays.stream(pp);
        }).toArray(Byte[]::new);
    }

    @State(Scope.Benchmark)
    public static class MyState {
        @Param({"0123456789abcdef", "00112233445566778899AABBCCDDEEFF"})
        public String i;
    }

    @Benchmark
    @Warmup(iterations = 2, time = 1, batchSize = 3)
    @Measurement (iterations = 2, time = 1, batchSize = 3)
    public void test(MyState state, Blackhole bh)  {
        bh.consume(exercice4(parseHexBinary(state.i)));
    }

    @Benchmark
    @Warmup(iterations = 2, time = 1, batchSize = 3)
    @Measurement (iterations = 2, time = 1, batchSize = 3)
    public void testA(MyState state, Blackhole bh)  {
        bh.consume(exercice4A(parseHexBinary(state.i)));
    }

    @Benchmark
    @Warmup(iterations = 2, time = 1, batchSize = 3)
    @Measurement (iterations = 2, time = 1, batchSize = 3)
    public void testB(MyState state, Blackhole bh)  {
        bh.consume(exercice4B(parseHexBinary(state.i)));
    }

    @Benchmark
    @Warmup(iterations = 2, time = 1, batchSize = 3)
    @Measurement (iterations = 2, time = 1, batchSize = 3)
    public void testC(MyState state, Blackhole bh)  {
        bh.consume(exercice4C(parseHexBinary(state.i)));
    }
}
