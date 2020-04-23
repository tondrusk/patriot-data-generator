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

/**
 * This class handles transitions between stateTransitions to simulate real usage of actuators.
 * It creates loop of States, that holds information about its status and time duration that is needed for State to finish its action.
 * Action is handled with instance StopWatch. Value from stopwatch is compared with duration from State on every interaction
 * with StateMachine to determinate if State should prevail or should be changed for next.
 * <p>
 * Extending (duration)
 * /                    \
 * Retracted (0.0)                     Extended (0.0)
 * \                    /
 * Retracting (duration)
 * <p>
 * This StateMachine is create as follows:
 * new StateMachine()
 * .addState("Retracted")
 * .addState("Extending", duration)
 * .addState("Extended")
 * .addState("Retracting", duration)
 * .build();
 * <p>
 * StateMachine uses two types of States:
 * - without duration or duration equals 0.0:
 * StateMachine is waiting for external input to change State to next in line
 * - with defined duration:
 * State needs to last set amount of time (duration). After time expires State is changed
 */
public final class StateMachine {

    //som v stave A, je cas B vytvorit interface co dalej spravit Condition?

    private List<State> states = new ArrayList<>();

    private Transition th;

    public StateMachine(List<State> states) {
        th = new ActiveTransitions(states.get(0));
        this.states = states;
    }

    public void transition(String event) {
        try {
            th.transition(event);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getCurrent() {
        try {
            return th.getCurrentState().get().getName();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return "NADA";
    }

    public void setDataForState(String state, int data) {
        states.stream()
                .filter(s -> s.getName().equals(state))
                .findFirst()
                .get()
                .setData(data);
    }

//    public void transition(String event, int progress) {
//        currentState = th.transition(event, currentState, progress);
//    }

//    public String status() {
//
//    }

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
//            stateTransitions.put(currentState, null);
            return this;
        }

        public Builder from(String name, double duration) {
            currentState = new ProgressionState(name, duration);
//            assignHaltState(currentState);
//            stateTransitions.put(currentState, null);
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
                stateTransitions.put(currentState, new HashMap<>(Map.of(event, nextState)));
            }
        }

//        private void assignHaltState(State state) {
//            State halt = new State("Halt");
//            state.addNextState(Transition.HALT, halt);
//            halt.addNextState(Transition.CONTINUE, state);
//        }

        /**
         * For
         *
         * @param state
         * @param transitions
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
