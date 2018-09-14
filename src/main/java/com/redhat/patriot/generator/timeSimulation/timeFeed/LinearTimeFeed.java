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

package com.redhat.patriot.generator.timeSimulation.timeFeed;

/**
 * Created by jsmolar on 9/11/18.
 */
public class LinearTimeFeed extends TimeFeed {

    private double period;

    private double time;

    public LinearTimeFeed(double period) {
        this.period = period;
        time = 0;
    }

    @Override
    public double getSimulatedTime() {
        time += period;

        return time;
    }

    @Override
    public double getTimeChange() {
        return period;
    }

    public double getPeriod() {
        return period;
    }

    public void setPeriod(double period) {
        this.period = period;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
