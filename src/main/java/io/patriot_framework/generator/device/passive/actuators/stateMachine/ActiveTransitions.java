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

public class ActiveTransitions implements Transition {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveTransitions.class);

//    private StopWatch sw = new StopWatch();

    private CompletableFuture<State> currentState;

    public ActiveTransitions(State currentState) {
        this.currentState = CompletableFuture.supplyAsync(() -> currentState);
    }

    @Override
    public Future<State> transition(String event) {
        return transition(event, 100);
    }

    @Override
    public Future<State> transition(String event, int completion) {
        if (!currentState.isDone()) return null;

        State processed = nextState(event);

        currentState = CompletableFuture.completedFuture(processed);
        LOGGER.info("Transitioned in to " + processed);

        if (processed instanceof ProgressionState) {
            long delay = (long) ((ProgressionState) processed).getDuration();
            delay *= completion/100;

            Executor delayed = CompletableFuture.delayedExecutor(delay, TimeUnit.MILLISECONDS);

            currentState = currentState.thenApplyAsync(s -> {
                State toReturn = s.getNextState(event);
                LOGGER.info("Transitioned in to " + toReturn);

                return toReturn;
            }, delayed);

        }

        return currentState;
    }

    @Override
    public CompletableFuture<State> getCurrentState() {
        return currentState;
    }

    private State nextState(String event) {
        try {
            return currentState.get().getNextState(event);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

}
