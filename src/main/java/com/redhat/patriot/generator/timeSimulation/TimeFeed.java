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

package com.redhat.patriot.generator.timeSimulation;

import net.objecthunter.exp4j.Expression;
import umontreal.ssj.randvar.RandomVariateGen;

/**
 * Created by jsmolar on 7/21/18.
 */
public class TimeFeed {

    private RandomVariateGen timeDistribution;

    private Expression expression;

    private Integer iteration = 0;

    public TimeFeed(RandomVariateGen timeDistribution) {
        this.timeDistribution = timeDistribution;
    }

    public TimeFeed(Expression expression) {
        this.expression = expression;
    }

    public double getTime() {
        if (timeDistribution != null) {
            return 0;
        } else if (expression != null) {
            expression.setVariable("y", iteration);
            iteration++;
            return expression.evaluate();
        } else {
            throw new IllegalArgumentException("Time Distribution or Expression needs to be defined");
        }
    }

}
