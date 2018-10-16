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

import umontreal.ssj.probdist.NormalDist;
import umontreal.ssj.randvar.NormalGen;
import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.rng.RandomStream;

/**
 * Created by jsmolar on 9/19/18.
 */
public class NormalDistributionDataFeed implements IndependentDataFeed {

    private NormalGen normalDist;

    public NormalDistributionDataFeed(double mu, double sigma) {
        RandomStream rs = new MRG32k3a();
        this.normalDist = new NormalGen(rs, mu, sigma);
    }

    @Override
    public double getNextValue() {
        double result = normalDist.nextDouble();
        //LOGGER.info("Generated data from Normal distribution Data feed: " + result);

        return result;
    }

    @Override
    public double getPreviousValue() {
        return 0;
    }

}
