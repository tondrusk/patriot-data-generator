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

import java.util.*;

import static io.patriot_framework.generator.utils.CustomCollectors.toSingleton;

/**
 * This class handles transitions between stateTransitions to simulate real usage of actuators.
 * It creates loop of States, that holds information about its status and time duration that is needed for State to finish its action.
 * Action is handled with instance StopWatch. Value from stopwatch is compared with duration from State on every interaction
 * with StateMachine to determinate if State should prevail or should be changed for next.
 *
 *                  Extending (duration)
 *                 /                    \
 *  Retracted (0.0)                     Extended (0.0)
 *                 \                    /
 *                 Retracting (duration)
 *
 *  This StateMachine is create as follows:
 *      new StateMachine()
 *          .addState("Retracted")
 *          .addState("Extending", duration)
 *          .addState("Extended")
 *          .addState("Retracting", duration)
 *          .build();
 *
 *  StateMachine uses two types of States:
 *      - without duration or duration equals 0.0:
 *          StateMachine is waiting for external input to change State to next in line
 *      - with defined duration:
 *          State needs to last set amount of time (duration). After time expires State is changed
 *
 */
public final class StateMachine {

    private List<State> states = new ArrayList<>();

    private State currentState;

    private Transition th = new PassiveTransitions();

    public StateMachine(List<State> states) {
        currentState = states.get(0);
        this.states = states;
    }

    public void transition(String event) {
        currentState = th.transition(event, currentState);
    }

    public void transition(String event, int progress) {
        currentState = th.transition(event, currentState, progress);
    }

    public static class Builder implements Buildable<StateMachine> {

        private final Map<State, Map<String, String>> stateTransitions = new LinkedHashMap<>();

        private State currentState;

        public Builder from(String name) {
            currentState = new State(name);
            stateTransitions.put(currentState, null);
            return this;
        }

        public Builder from(String name, double duration) {
            currentState = new ProgressionState(name, duration);
            assignHaltState(currentState);
            stateTransitions.put(currentState, null);
            return this;
        }

        public Builder to(String name) {
            stateTransitions.put(currentState, Map.of(Transition.EPSILON, name));
            return this;
        }

        public Builder to(String name, String event) {
            stateTransitions.put(currentState, Map.of(event, name));
            return this;
        }

        private void assignHaltState(State state) {
            State halt = new State("Halt");
            state.addNextState(Transition.HALT, halt);
            halt.addNextState(Transition.CONTINUE, state);
        }

        private void processState(State state, Map<String, String> transitions) {
            Map<String, State> result = new HashMap<>();

            transitions.forEach((key, value) -> result.put(key, stateTransitions.keySet().stream()
                    .filter(s -> s.getName().equals(value))
                    .collect(toSingleton())));

            state.setNextStates(result);
        }

        @Override
        public StateMachine build() {
            stateTransitions.forEach(this::processState);

            return new StateMachine(new ArrayList<>(stateTransitions.keySet()));
        }

    }

}
