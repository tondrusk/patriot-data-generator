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
    private String name;

    /**
     * If state should last set about of time duration should be greater than 0.0
     */

    private Map<String, State> nextStates = new HashMap<>();

    private String previousEvent;
    private State previousState;

//    private AbstractMap.SimpleEntry<String, State> previousState;

    public State(String description) {
        this.name = description;
    }

    public State getNextState(String even) {
        return nextStates.get(even);
    }

    public void addNextState(String event, State state) {
        this.nextStates.put(event, state);
    }

    public void setNextStates(Map<String, State> destinations) {
        this.nextStates = destinations;
    }

    public String getPreviousEvent() {
        return previousEvent;
    }

    public void setPreviousEvent(String previousEvent) {
        this.previousEvent = previousEvent;
    }

    public State getPreviousState() {
        return previousState;
    }

    public void setPreviousState(State previousState) {
        this.previousState = previousState;
    }

    //    public AbstractMap.SimpleEntry<String, State> getPreviousState() {
//        return previousState;
//    }
//
//    public void setPreviousState(AbstractMap.SimpleEntry<String, State> previousState) {
//        this.previousState = previousState;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "State{" +
                "name='" + name + '\'' +
                '}';
    }
}