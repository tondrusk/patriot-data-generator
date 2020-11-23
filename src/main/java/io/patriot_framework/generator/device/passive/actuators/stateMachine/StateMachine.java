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

import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * This class handles {@link Transition}-s between {@link State}-s to Actuator to simulate specific scenarios.
 * It logical structure of States, that holds information about its status and time duration that is needed for State to finish its action.
 * {@link Transition} directs all necessary transitions, while {@link State} hold all necessary information.
 *
 * To create correctly configured instance of StateMachine, builder pattern was implemented.
 * Example:
 *
 * <p>
 *                  Off
 *          /                    \
 * Stopping (duration)        Starting (duration)
 *          \                    /
 *                  On
 * <p>
 *
 *  new StateMachine.Builder()
 *                  .from("Off")
 *                      .to("On")
 *                  .from("On")
 *                      .to("Off")
 *                  .build()
 * <p>
 *
 */
public final class StateMachine {

    private List<State> states;

    private Transition th;

    public StateMachine(List<State> states) {
        th = new ActiveTransition(states.get(0));
        this.states = states;
    }

    /**
     * Method that triggers actions in a form of transitions
     *
     * @param event input event for StateMachine
     */
    public void transition(String event) {
        th.transition(event);
    }

    /**
     * Requests StateMachine to return current State
     *
     * @return the name of current State
     * @throws ExecutionException if the transition threw an exception
     * @throws InterruptedException if the current tread was interrupted while waiting for transition
     */
    public String getCurrent() throws ExecutionException, InterruptedException {
        Future<State> current = th.getFutureState();

        return current.isDone() ? current.get().getName() : null;
    }

    public void setDataForState(String state, int data) {
        states.stream()
                .filter(s -> s.getName().equals(state))
                .findFirst()
                .get()
                .setData(data);
    }

    public static class Builder implements Buildable<StateMachine> {

        /**
         * Variable saves all states and transitions to be processed once build() method is called.
         * Nested maps are in format:
         *      [Origin State, [Transition Event, Next State]]
         */
        private Map<State, Map<String, String>> stateTransitions = new LinkedHashMap<>();

        private State currentState;

        public Builder from(String name) {
            currentState = new State(name);
            return this;
        }

        public Builder from(String name, double duration) {
            currentState = new ProgressionState(name, duration);
            return this;
        }

        public Builder to(String name) {
            addTransition(currentState, RandomStringUtils.random(7, true, true), name);
            return this;
        }

        public Builder to(String name, String event) {
            addTransition(currentState, event, name);
            return this;
        }

        public Builder to(String name, String event, String via, double duration) {
            State intermediate = new ProgressionState(via, duration);
            addTransition(currentState, event, via);
            addTransition(intermediate, event, name);
            return this;
        }

        public Builder condition(Condition con, int data) {
            currentState.setCondition(con);
            currentState.setData(data);
            return this;
        }

        private void addTransition(State currentState, String event, String nextState) {
            if (stateTransitions.containsKey(currentState)) {
                stateTransitions.get(currentState)
                        .put(event, nextState);
            } else {
                stateTransitions.put(
                        currentState,
                        new HashMap<String, String>() {{
                            put(event, nextState);
                        }}
                );
//                stateTransitions.put(currentState, new HashMap<>(Map.of(event, nextState)));
//                Use when switched to java version 9+
            }
        }

        /**
         * For every {@link State} find and assign correct transitions. Building process creates
         * raw image of the whole StateMachine where transition function consists of two Strings
         * (Event, State). This method is tasked to assign correct instance of State based on this String value.
         *
         * @param state State that is being processed in this method
         * @param transitions Map (Event, State) that has to be converted on correct State instances
         */
        private void processState(State state, Map<String, String> transitions) {
            Map<String, State> result = new HashMap<>();

//            snazim sa najst spravny state pre dany nazov v transition pre state
            if (transitions != null) {
                transitions.forEach((key, value) -> result.put(key, stateTransitions.keySet().stream()
                        .filter(s -> s.getName().equals(value))
                        .findAny()
                        .orElse(new State(value))));
            }


            state.setNextStates(result);
        }

        @Override
        public StateMachine build() {
            stateTransitions.forEach(this::processState);

            return new StateMachine(new ArrayList<>(stateTransitions.keySet()));
        }
    }

}
