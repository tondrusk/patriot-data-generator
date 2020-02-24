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

package io.patriot_framework.generator.device.passive.actuators;

import java.util.HashMap;
import java.util.Map;

/**
 * State nodes for StateMachine. Used for creating loop of states to simulate actions of Actuator.
 */
public class State {

    /**
     * Name of representing state (e.g. Started, Stopped, Extracting...)
     */
    private String description;

    /**
     * If state should last set about of time duration should be greater than 0.0
     */
    private double duration;

    private Map<Events, State> nextStates = new HashMap<>();

    public State(String description, double duration) {
        this.description = description;
        this.duration = duration;
    }

    public State(String description) {
        this.description = description;
        this.duration = -1;
    }

    public Map<Events, State> getNextState() {
        return nextStates;
    }

    public void addNextState(Events event, State state) {
        this.nextStates.put(event, state);
    }

    public void setNextStates(Map<Events, State> destinations) {
        this.nextStates = destinations;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "State{" +
                "description='" + description + '\'' +
                ", duration=" + duration +
                '}';
    }
}