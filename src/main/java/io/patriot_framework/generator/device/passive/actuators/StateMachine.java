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

import org.apache.commons.lang3.time.StopWatch;

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
public class StateMachine {

    private List<State> states = new ArrayList<>();

    private State currentState;

    private StopWatch sw = new StopWatch();

    public StateMachine(List<State> states) {
        this.states = states;
    }

    public void trigger() {

    }

    public void trigger(Events event) {

    }

    public static class Builder implements Buildable<StateMachine> {

        private final Map<State, Map<Events, String>> stateTransitions = new LinkedHashMap<>();

        private State currentState;

        public Builder from(String description) {
            currentState = new State(description);
            stateTransitions.put(currentState, null);
            return this;
        }

        public Builder from(String description, double duration) {
            currentState = new State(description, duration);
            stateTransitions.put(currentState, null);
            return this;
        }

        public Builder to(String description) {
            stateTransitions.put(currentState, Map.of(Events.DEFAULT, description));
            return this;
        }

        public Builder to(String description, Events event) {
            stateTransitions.put(currentState, Map.of(event, description));
            return this;
        }


        private void processState(State state, Map<Events, String> transitions) {
            Map<Events, State> result = new HashMap<>();

            transitions.forEach((key, value) -> result.put(key, stateTransitions.keySet().stream()
                    .filter(s -> s.getDescription().equals(value))
                    .collect(toSingleton())));

            state.setNextStates(result);
        }

        @Override
        public StateMachine build() {
            stateTransitions.forEach(this::processState);

            return new StateMachine(new ArrayList<>(stateTransitions.keySet()));
        }

    }



//
//    private State current = null;
//    private int position = 0;
//
//    /**
//     * Returns current status of StateMachine.
//     * At first method validate, if current State is valid. If not, StateMachine will execute steps correct this.
//     *
//     * @return current StateMachine status
//     */
//    public String status() {
//        checkProgress();
//
//        if (sw.isStarted()) {
//            return current.getDescription() + ": " + progress() + "%";
//        } else {
//            return current.getDescription();
//        }
//    }
//
//    public boolean transition() {
//        if (!checkProgress()) {
//            next();
//            sw.start();
//
//            return true;
//        }
//
//        return false;
//    }
//
//    private boolean checkProgress() {
//        if (sw.getTime() >= current.getDuration() && current.isTimeDependent()) {
//            sw.reset();
//            next();
//
//            return true;
//        }
//
//        return false;
//    }
//
//    private void next() {
//        position++;
//        if (position >= stateTransitions.size() ) {
//            position = 0;
//        }
//        current = stateTransitions.get(position);
//    }
//
//    public StateMachine addState(String stateName) {
//        stateTransitions.add(new State(stateName));
//        return this;
//    }
//
//    public StateMachine addState(String stateName, double stateDuration) {
//        stateTransitions.add(new State(stateName, stateDuration));
//        return this;
//    }
//
//    public StateMachine build() {
//        current = stateTransitions.get(0);
//        return this;
//    }
//
//    private double progress() {
//        double result = sw.getTime() / current.getDuration() * 100;
//
//        BigDecimal bd = new BigDecimal(result);
//        bd = bd.setScale(2, RoundingMode.HALF_UP);
//        return bd.doubleValue();
//    }

}
