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

import net.objecthunter.exp4j.Expression;

import java.util.Set;

/**
 * Created by jsmolar on 9/9/18.
 */
public class ExpressionDataFeed implements DataFeed<Double> {

    private Expression expression;

    private String variable;

    private double previousValue;

    public ExpressionDataFeed(Expression expression) {
        this.expression = expression;
        validate();
    }

    private void validate() {
        if(expression.validate().isValid()) {
            Set<String> variables = expression.getVariableNames();
            if(variables.size() != 1) {
                // Handle invalid expression
            }

            variable = (String) variables.toArray()[0];
        } else {
            // Throw exception
        }
    }

    @Override
    public Double getNextValue(Object... params) {
        double time = (double) params[0];
        expression.setVariable(variable, time);
        double result = expression.evaluate();
        previousValue = result;
//        LOGGER.info("Generated data from expression: " + result);

        return result;
    }

    @Override
    public Double getPreviousValue() {
        return previousValue;
    }
}
