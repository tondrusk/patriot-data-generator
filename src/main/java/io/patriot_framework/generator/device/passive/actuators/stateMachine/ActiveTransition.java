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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 *  Implementation of {@link Transition} that perform correct transitions for {@link State}-s,
 *  that are configured to last specific time period, before another actions can be taken {@link ProgressionState}.
 *
 *  {@link CompletableFuture} was used to store actual or future (State that is scheduled for computation) State.
 *  This allows detection of current State or State that is yet to be completed.
 */
public class ActiveTransition implements Transition {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveTransition.class);

    /**
     * Variable that tracks current State
     */
    private CompletableFuture<State> futureState;

    /**
     * During initialization of ActiveTransition, the initial state of StateMachine is used to create
     * futureState and is instantly completed.
     *
     * @param futureState
     */
    public ActiveTransition(State futureState) {
        this.futureState = CompletableFuture.supplyAsync(() -> futureState);
    }

    /**
     * Method is called when Actuator/StateMachine is required to perform transition.
     * Transition called on {@link State}, it will verify if this state has successor
     * related with param event and immediately perform transition.
     * If transition is called on {@link ProgressionState}, it will delay execution
     * of {@link CompletableFuture}
     *
     * @param event
     * @return
     */
    @Override
    public Future<State> transition(String event) {
        // Proceed only if transitions are completed
        if (!futureState.isDone()) {
            LOGGER.info("Transition request is denied, current State is still in progress");
            return null;
        }

        State processed = getNextStateFromFuture(event);
        if (processed == null)
            return null;

        futureState = CompletableFuture.completedFuture(processed);
        LOGGER.info("Transitioned in to " + processed);
        /*
         * At this point, StateMachine transitioned in to correct State based on event
         * If this State is ProgressionState, StateMachine has to schedule another
         * transition to desired State.
         */

        if (processed instanceof ProgressionState) {
            long delay = (long) ((ProgressionState) processed).getDuration();

            Executor delayed = CompletableFuture.delayedExecutor(delay, TimeUnit.MILLISECONDS);

            futureState = futureState.thenApplyAsync(s -> {
                State toReturn = s.getNextState(event);
                LOGGER.info("Transitioned in to " + toReturn);

                return toReturn;
            }, delayed);

        }

        return futureState;
    }

    @Override
    public CompletableFuture<State> getFutureState() {
        return futureState;
    }

    /**
     *
     * @return
     */
    private State getNextStateFromFuture(String event) {
        /*
          Mocked State that is returned to the user if {@link StateMachine} did not completed Future
         */
        State busyState = new State("Busy");
        State state = futureState.getNow(busyState);

        if (state.getNextState(event) == null) {
            LOGGER.info("No transition available from State: " + state + " under event: " + event);
            return null;
        }

        return state.getNextState(event);
    }

}
