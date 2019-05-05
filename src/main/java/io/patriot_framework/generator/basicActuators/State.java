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

package io.patriot_framework.generator.basicActuators;

import org.apache.commons.lang3.time.StopWatch;

public class State {

    private StopWatch sw = new StopWatch();

    private String value;
    private double duration;
    private boolean isTimeDependent;

    public State(String value, double duration) {
        this.value = value;
        this.duration = duration;
        this.isTimeDependent = true;
    }

    public State(String value) {
        this.value = value;
        this.duration = 0.0;
        this.isTimeDependent = false;
    }


    public String getStatus() {
        return "a";
    }




    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public boolean isTimeDependent() {
        return isTimeDependent;
    }

    public void setTimeDependent(boolean timeDependent) {
        this.isTimeDependent = timeDependent;
    }

    @Override
    public String toString() {
        return "State{" +
                "value='" + value + '\'' +
                ", duration=" + duration +
                '}';
    }
}