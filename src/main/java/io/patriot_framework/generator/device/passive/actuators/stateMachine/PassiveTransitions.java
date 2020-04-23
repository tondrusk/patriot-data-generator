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

public class PassiveTransitions implements Transition {


    @Override
    public Future<State> transition(String event) {
        return null;
    }

    @Override
    public Future<State> transition(String event, int completion) {
        return null;
    }

    @Override
    public CompletableFuture<State> getCurrentState() {
        return null;
    }


//    private StopWatch sw = new StopWatch();
//
//    @Override
//    public Future<State> transition(String event, State current) {
//        if (sw.isStopped()) sw.start();
//
//        State processed = validateCurrentState(event, current, sw.getTime());
//
//        if (processed instanceof ProgressionState) {
//            if (sw.getTime() > countCompareTime(processed)) {
//                sw.reset();
//
//                return processed.getNextState(event);
//            }
//        }
//
////        return processed.getNextState(event);
//        return null;
//    }
//
//    @Override
//    public Future<State> transition(String event, State current, int completion) {
//        ProgressionState progressionState = (ProgressionState) transition(event, current);
//        progressionState.setProgress(completion);
//
//        return null;
//    }
//
//    private State validateCurrentState(String inputEvent, State state, double time) {
//
//        if (state instanceof ProgressionState) {
//            ProgressionState progressionState = (ProgressionState) state;
//
//            if (checkProgression(progressionState, time)) return progressionState.getNextState(Transition.HALT);
//
//            if (progressionState.getProgress() > sw.getTime()) {
//                State nextState;
//
//                nextState = Optional
//                        .ofNullable(state.getNextState(inputEvent))
//                        .orElse(state.getNextState(Transition.EPSILON));
//
//                if (nextState != null) validateCurrentState(inputEvent, nextState, time - progressionState.getDuration());
//            }
//        }
//
//        // Maybe add epsilon transitions
//
//        return state;
//    }
//
//    private boolean checkProgression(ProgressionState state, double time) {
//        if (state.getProgress() > 0) {
//            double maxStateDuration = state.getDuration() * state.getProgress() / 100;
//
//            return time > maxStateDuration;
//        }
//
//        return false;
//    }
//
//    private double countCompareTime(State state) {
//        ProgressionState progressionState = (ProgressionState) state;
//
//        return progressionState.getProgress() > 0
//                ? progressionState.getProgress() : progressionState.getDuration();
//    }

}
