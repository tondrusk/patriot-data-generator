/*
 * Copyright 2020 Patriot project
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
import umontreal.ssj.probdist.ContinuousDistribution;
import umontreal.ssj.probdist.ExponentialDist;

import java.util.Objects;

/**
 * DataFeed based on {@link ContinuousDistribution}.
 */
public class ExponentialDistDataFeed implements DataFeed {

    private String label;

    private ContinuousDistribution dist;

    @JsonProperty
    private double previousValue;

    @JsonProperty
    private double lambda;

    @JsonCreator
    public ExponentialDistDataFeed(@JsonProperty("lambda") double lambda) {
        this.lambda = lambda;
        this.dist = new ExponentialDist(lambda);
    }

    /**
     * Generates next value for Exponential Distribution.
     *
     * @param params value at which the distribution function is evaluated
     * @return
     */
    @Override
    public Data getNextValue(Object... params) {
        if (params.length < 1)
            throw new IllegalArgumentException("At least one parameter is required");

        Double result;
        result = dist.cdf((Double) params[0]);
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
        if (!(o instanceof ExponentialDistDataFeed)) return false;
        ExponentialDistDataFeed that = (ExponentialDistDataFeed) o;
        return Double.compare(that.previousValue, previousValue) == 0 && Double.compare(that.lambda, lambda) == 0 && Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, previousValue, lambda);
    }
}
