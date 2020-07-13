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

package io.patriot_framework.generator.device.passive.actuators.stateMachine;

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
     * Map representing [event, next State] pairs
     */
    private Map<String, State> nextStates = new HashMap<>();

    /**
     * Condition associated with state that is valuated during transition
     */
    private Condition condition;

    /**
     * Simple variable that holds arbitrary information
     * Note: this is subject of change in the future to more volatile representation
     */
    private int data;

    private String description;

    public State(String description) {
        this.name = description;
    }

    public State getNextState(String even) {
        State next = nextStates.get(even);

        if (condition != null) {
            String tmp = condition.con(data);

            next = nextStates.values()
                      .stream()
                      .filter(state -> tmp.equals(state.getName()))
                      .findFirst()
                      .get();
        }

        return next;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public void addNextState(String event, State state) {
        this.nextStates.put(event, state);
    }

    public void setNextStates(Map<String, State> destinations) {
        this.nextStates = destinations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "State{" +
                "name='" + name + '\'' +
                '}';
    }
}