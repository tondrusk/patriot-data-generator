/*
 * Copyright 2018 Patriot project
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

package com.redhat.patriot.generator.device.active;

import com.redhat.patriot.generator.dataFeed.IndependentDataFeed;
import com.redhat.patriot.generator.dataFeed.TimeDependentDataFeed;
import com.redhat.patriot.generator.device.AbstractDevice;
import com.redhat.patriot.generator.timeSimulation.timeFeed.TimeFeed;

import java.util.Timer;
import java.util.TimerTask;

public class AbstractActiveDevice extends AbstractDevice implements ActiveDevice {

    private TimeFeed timeFeed;

    private Timer timer;

    public AbstractActiveDevice() {
        timer = new Timer();
    }

    @Override
    protected AbstractDevice getThis() {
        return this;
    }

    @Override
    public void simulate() {
        timer.schedule(task(), 0);
    }

    @Override
    public void stopSimulation() {
        timer.cancel();
        timer.purge();
    }

    private double generateValue(double time) {
        double value = 0;

        if(dataFeed instanceof IndependentDataFeed) {
           value = ((IndependentDataFeed) dataFeed).getNextValue();
        } else if(dataFeed instanceof TimeDependentDataFeed) {
           value = ((TimeDependentDataFeed) dataFeed).getNextValue(time);
        }

        return value;
    }

    private void sendData(double data) {
        dataWrapper.wrapData(this, data);
        dataWrapper.send();
    }

    private TimerTask task() {
        return new TimerTask() {
            @Override
            public void run() {
                System.out.println("Thread: " + Thread.currentThread().getId());
                double simTime = timeFeed.getSimulatedTime();
                LOGGER.info("Simulated time: " + (simTime/1000) % 24);

                sendData(generateValue(simTime));
                timer.schedule(task(), Math.round(timeFeed.getTimeChange()));
            }
        };
    }

    public TimeFeed getTimeFeed() {
        return timeFeed;
    }

    public AbstractDevice setTimeFeed(TimeFeed timeFeed) {
        this.timeFeed = timeFeed;
        return this;
    }

}
