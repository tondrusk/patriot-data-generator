/*
 * Copyright 2018 Patriot project
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.redhat.patriot.generator.dataFeed;

/**
 * Created by jsmolar on 7/6/18.
 */
//Seed should be in format: the first 3 values of the seed must all be less than m1=4294967087, and not all 0;
//and the last 3 values must all be less than m2=4294944443, and not all 0.
public class Seed {

    private static final long M1 = 4294967087L;
    private static final long M2 = 4294944443L;

    public static long[] generate() {
        long[] seed = {random(M1), random(M1), random(M1), random(M2), random(M2), random(M2)};
        return seed;
    }

    private static long random(long limit) {
        return 1 + (long) (Math.random() * (limit - 1));
    }

}
