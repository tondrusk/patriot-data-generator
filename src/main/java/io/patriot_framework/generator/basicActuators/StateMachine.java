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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class StateMachine {

    private StopWatch sw = new StopWatch();

    private List<State> states = new ArrayList<>();
    private State current = null;
    private int position = 0;

    public String status() {
        checkProgress();

        if (sw.isStarted()) {
            return current.getValue() + ": " + progress() + "%";
        } else {
            return current.getValue();
        }
    }

    public boolean transition() {
        if (!checkProgress()) {
            next();
            sw.start();
        }

        return true;
    }

    private boolean checkProgress() {
        if (sw.getTime() >= current.getDuration() && current.isTimeDependent()) {
            sw.reset();
            next();

            return true;
        }

        return false;
    }

    private void next() {
        position++;
        if (position > states.size()) {
            position = 0;
        }
        current = states.get(position);
    }

    public StateMachine addState(String stateName) {
        states.add(new State(stateName));
        return this;
    }

    public StateMachine addState(String stateName, double stateDuration) {
        states.add(new State(stateName, stateDuration));
        return this;
    }

    public StateMachine build() {
        current = states.get(0);
        return this;
    }

    private double progress() {
        double result = sw.getTime() / current.getDuration() * 100;

        BigDecimal bd = new BigDecimal(result);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public State getCurrent() {
        return current;
    }

    public void setCurrent(State current) {
        this.current = current;
    }
}
