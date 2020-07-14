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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Transition are used by {@link StateMachine} to logically specify an reaction of StateMachine
 * on incoming event. If desired, Transition can perform transitions even without
 * input event, this is required by StateMachine to stay in valid and consistent state.
 *
 * It operates with transition functions that are provided by the user and operational logic,
 * defined in particular implementation of Transition. Transition function are stored inside
 * {@link State} in a form of a Map, where every State holds information about available transitions.
 *
 * Transition is held responsible for:
 *      1. Transition between states based on events or specified logic
 *      2. Input event that would trigger transition should be validated against rules defined by
 *         specific implementation of Transition
 *      3. Keeping StateMachine in consistent state
 *      4. When requested, reliably return current state
 */
public interface Transition {

    String EPSILON = "epsilon";
    String UP = "up";
    String DOWN = "down";

    String HALT = "halt";
    String CONTINUE = "continue";

    String CONDITION = "condition";

    /**
     * Method should contain logic that would happen when {@link StateMachine} receives an event,
     * more specifically, when method {@link StateMachine#transition(String)} is invoked.
     *
     * This is the method takes in to account:
     *      1. Current {@link State} the StateMachine is present
     *      2. Transition function (set of all possible next States defined in current State)
     *      3. Implementation specific logic (validation, conditions etc.)
     *
     * Based on these information do or do not perform transition.
     *
     * @param event input event that is compared with Map of possible next states for current State
     * @return Future of an current {@link State}
     */
    Future<State> transition(String event);

    /**
     * Returns instance of CompletableFuture. This defines current state of StateMAchine
     *
     * @return CompletableFuture with reference to State
     */
    CompletableFuture<State> getFutureState();

}
