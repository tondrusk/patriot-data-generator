/*
 * Copyright 2019 Patriot project
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

package io.patriot_framework.generator.dataFeed;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.patriot_framework.generator.Data;
import umontreal.ssj.randvar.NormalGen;
import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.rng.RandomStream;

import java.util.Objects;

/**
 * DataFeed based on {@link NormalGen}.
 */
public class NormalDistVariateDataFeed implements DataFeed {

    private String label;

    private NormalGen normalGen;

    @JsonProperty
    private double previousValue;

    @JsonProperty
    private double mu;

    @JsonProperty
    private double sigma;

    @JsonCreator
    public NormalDistVariateDataFeed(@JsonProperty("mu") double mu,
                                     @JsonProperty("sigma") double sigma) {
        this.mu = mu;
        this.sigma = sigma;
        RandomStream rs = new MRG32k3a();
        this.normalGen = new NormalGen(rs, mu, sigma);
    }

    /**
     * Generates next value for Normal Distribution Variant.
     * {@link NormalGen} does not require any parameter for its calculations.
     * To change generated data stream use method {@link MRG32k3a#setPackageSeed(long[])}
     *
     * @param params should be empty
     * @return next evaluated value
     */
    @Override
    public Data getNextValue(Object... params) {
        Double result;
        result = normalGen.nextDouble();
        previousValue = result;

        return new Data(Double.class, result);
    }

    @Override
    public Data getPreviousValue() {
        return new Data(Double.class, previousValue);
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NormalDistVariateDataFeed that = (NormalDistVariateDataFeed) o;
        return Double.compare(that.previousValue, previousValue) == 0 && Double.compare(that.mu, mu) == 0 && Double.compare(that.sigma, sigma) == 0 && Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, previousValue, mu, sigma);
    }
}
