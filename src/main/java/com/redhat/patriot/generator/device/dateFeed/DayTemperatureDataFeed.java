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

package com.redhat.patriot.generator.device.dateFeed;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * Created by jsmolar on 9/9/18.
 */
public class DayTemperatureDataFeed extends DataFeed {

    private double dayMin;
    private double dayMax;

    private static final int T_MIN = 5;
    private static final int T_MAX = 16;

    private static final Expression F1 = new ExpressionBuilder("( cos( pi * ("+ T_MIN +" - t ) / 24 + pi * " +  T_MIN + "/24 - pi * " + T_MAX + "/24) + 1 ) / 2")
        .variable("t")
        .build();

    private static final Expression F2 = new ExpressionBuilder("(cos(pi * (t-" + T_MIN + ")/" + T_MAX + "-" + T_MIN + ")+1)/2")
        .variable("t")
        .build();

    private static final Expression F3 = new ExpressionBuilder("(cos(pi(24+" + T_MIN + "-t)/24+" + T_MIN + "-"+ T_MAX + ")+1)/2")
        .variable("t")
        .build();

    public DayTemperatureDataFeed(float dayMin, float dayMax) {
        this.dayMin = dayMin;
        this.dayMax = dayMax;
    }

    @Override
    public double getValue(double time) {
        double result = 0;

        time = (time/1000) % 24;

        if(time < T_MIN) {
            F1.setVariable("t", time);
            double a = F1.evaluate();
            double second = 1 - a;
            result = (a * dayMin) + (second * dayMax);
        } else if(T_MIN < time && time < T_MAX) {
            F2.setVariable("t", time);
            double a = F2.evaluate();
            double second = 1 -a;
            result = (a * dayMin) + (second * dayMax);
        } else if(T_MIN < time) {
            F3.setVariable("t", time);
            double a = F3.evaluate();
            double second = 1 - a;
            result = (a * dayMin) + (second * dayMax);
        }

        LOGGER.info("Day temperature is: " + result);

        return result;
    }
}
