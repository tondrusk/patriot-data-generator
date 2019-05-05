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

package io.patriot_framework.generator.device.passive;

import io.patriot_framework.generator.Data;
import io.patriot_framework.generator.basicActuators.StateMachine;
import io.patriot_framework.generator.controll.ActuatorCoapController;
import io.patriot_framework.generator.device.AbstractDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public abstract class AbstractActuator extends AbstractDevice implements Actuator {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractActuator.class);

    private StateMachine stateMachine;

    private int max;
    private int min;
    private double duration;
    private boolean state;

    public AbstractActuator(String label) {
        super(label);
        setCoapController(new ActuatorCoapController(this));
    }

    @Override
    public List<Data> requestData(Object... params) {
        return Collections.singletonList(new Data(String.class, stateMachine.status()));
    }

    @Override
    public void controlSignal() {
        stateMachine.transition();
    }

//    public abstract String evaluate(double result);

    @Override
    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public double getDuration() {
        return duration;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public StateMachine getStateMachine() {
        return stateMachine;
    }

    public void setStateMachine(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }
}
