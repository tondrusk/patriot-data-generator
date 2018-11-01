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

import com.redhat.patriot.generator.dataFeed.DataFeed;
import com.redhat.patriot.generator.device.AbstractDevice;

import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractActiveDevice extends AbstractDevice implements ActiveDevice {

    private DataFeed timeFeed;

    private Timer timer;

    public AbstractActiveDevice(String label) {
        super(label);
    }

    public AbstractActiveDevice(String label, DataFeed dataFeed, DataFeed timeFeed) {
        super(label, dataFeed);
        this.timeFeed = timeFeed;
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

    private double generateValue(Object... param) {
        return dataFeed.getNextValue();
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
                double simTime = timeFeed.getNextValue();
                LOGGER.info("Simulated time: " + (simTime/1000) % 24);

                sendData(generateValue(simTime));
                double nextTask = simTime - timeFeed.getPreviousValue();
                timer.schedule(task(), Math.round(nextTask));
            }
        };
    }

    public DataFeed getTimeFeed() {
        return timeFeed;
    }

    public AbstractDevice setTimeFeed(DataFeed timeFeed) {
        this.timeFeed = timeFeed;
        return this;
    }

}
