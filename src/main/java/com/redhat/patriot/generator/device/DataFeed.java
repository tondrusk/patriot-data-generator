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

package com.redhat.patriot.generator.device;

import net.objecthunter.exp4j.Expression;
import umontreal.ssj.probdist.PoissonDist;
import umontreal.ssj.randvar.PoissonGen;
import umontreal.ssj.randvar.RandomVariateGen;
import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.rng.RandomStream;

/**
 * Created by jsmolar on 7/24/18.
 */
public class DataFeed {

    private RandomVariateGen randomVariate;

    private Expression expression;

    private Integer iteration = 0;

    public DataFeed(double lambda) {
        MRG32k3a.setPackageSeed(Seed.generate());
        RandomStream randomStream = new MRG32k3a();

        randomVariate = new PoissonGen(randomStream, new PoissonDist(lambda));
    }

    public DataFeed(Expression expression) {
        this.expression = expression;
    }

    public double getValue() {
        if (randomVariate != null) {
            return randomVariate.nextDouble();
        } else if (expression != null) {
            expression.setVariable("y", iteration);
            iteration++;
            return expression.evaluate();
        } else {
            throw new IllegalArgumentException("RandomVariantGen or Expression needs to be defined for DataFeed");
        }
    }

    //        public void poissonDist() {
    //            RandomStream rng = new MRG32k3a();
    //            MRG32k3a.setPackageSeed(Seed.generate());
    //
    //            RandomStream streamDemand = rng;
    //            rng.resetStartStream();
    //
    //            RandomVariateGenInt genDemand = new PoissonGen(streamDemand, new PoissonDist(lambda));
    //            randomValues = genDemand;
    //        }

}
