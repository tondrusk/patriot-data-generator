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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public interface Transition {

    String EPSILON = "epsilon";
    String UP = "up";
    String DOWN = "down";

    String HALT = "halt";
    String CONTINUE = "continue";

    String CONDITION = "condition";

    Future<State> transition(String event) throws ExecutionException, InterruptedException;

    Future<State> transition(String event, int completion);

    CompletableFuture<State> getCurrentState();



//    State halt(State current);
//
//    State resume(String event, State current);

}
