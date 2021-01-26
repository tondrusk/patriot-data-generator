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

import java.util.Objects;

/**
 * DataFeed generates same predefined value on every request.
 */
public class ConstantDataFeed implements DataFeed {

    private String label;

    @JsonProperty
    private double constant;

    @JsonCreator
    public ConstantDataFeed(@JsonProperty("constant") double constant) {
        this.constant = constant;
    }

    @Override
    public Data getNextValue(Object... params) {
        return new Data(Double.class, constant);
    }

    @Override
    public Data getPreviousValue() {
        return new Data(Double.class, constant);
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
        if (!(o instanceof ConstantDataFeed)) return false;
        ConstantDataFeed that = (ConstantDataFeed) o;
        return Double.compare(that.constant, constant) == 0 && Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, constant);
    }
}
