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

import io.patriot_framework.generator.Data;
import io.patriot_framework.generator.controll.server.ActuatorCoapController;
import io.patriot_framework.generator.device.AbstractDevice;
import io.patriot_framework.generator.device.passive.actuators.stateMachine.StateMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public abstract class AbstractActuator extends AbstractDevice implements Actuator {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractActuator.class);

    private StateMachine stateMachine;

    private boolean isEnabled = true;

    public AbstractActuator() {
    }

    public AbstractActuator(String label) {
        super(label);
        setCoapController(new ActuatorCoapController(this));
    }

    @Override
    public List<Data> requestData(Object... params) {
        if (!isEnabled) return null;
        String state = "";

        try {
            state = stateMachine.getCurrent();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return Collections.singletonList(new Data(String.class, state));
    }

    @Override
    public void controlSignal(String event) {
        stateMachine.transition(event);
    }

    //toto zmenit do device??
    @Override
    public void registerToCoapServer() {
        getCoapController().registerDevice();
    }

    @Override
    public StateMachine getStateMachine() {
        return stateMachine;
    }

    @Override
    public void setStateMachine(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

}
