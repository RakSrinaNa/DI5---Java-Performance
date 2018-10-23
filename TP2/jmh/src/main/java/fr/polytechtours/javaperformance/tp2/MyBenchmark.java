/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package fr.polytechtours.javaperformance.tp2;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class MyBenchmark {



    @State(Scope.Thread)
    public static class MyState {
        @Param ({"5", "10"})
        public int i;
    }

    @Benchmark
    @Warmup (iterations = 2, time = 8, batchSize = 3)
    @Measurement (iterations = 2, time = 8, batchSize = 3)
    public void testFact(MyState state, Blackhole bh) {
        bh.consume(fact(state.i));
    }

    @Benchmark
    @Warmup (iterations = 2, time = 8, batchSize = 3)
    @Measurement (iterations = 2, time = 8, batchSize = 3)
    public void testFactRec(MyState state, Blackhole bh) {
        bh.consume(factRec(state.i));
    }

    public static int fact(int j){
        int res = 1;
        for (int i = 1; i <= j; i++) {
            res *= i;
        }
        return res;
    }

    public static int factRec(int j){
        if(j <= 1)
            return 1;
        return j * factRec(j-1);
    }

}
