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

package com.redhat.patriot.generator.device.timeSimulation;

import com.redhat.patriot.generator.dataFeed.DataFeed;
import com.redhat.patriot.generator.device.Device;

import java.util.Timer;
import java.util.TimerTask;

public class TimeSimulationImpl implements TimeSimulation {

    private Timer timer = new Timer();

    private DataFeed timeFeed;

    private Device device;

    public TimeSimulationImpl() {
    }

    public TimeSimulationImpl(DataFeed timeFeed, Device device) {
        this.timeFeed = timeFeed;
        this.device = device;
    }

    @Override
    public void setDevice(Device device) {
        this.device = device;
    }

    @Override
    public void setTimeFeed(DataFeed timeFeed) {
        this.timeFeed = timeFeed;
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

    private TimerTask task() {
        return new TimerTask() {
            @Override
            public void run() {
                System.out.println("Thread: " + Thread.currentThread().getId());
                double simTime = timeFeed.getNextValue();

                double data = device.requestData(simTime);
                System.out.println(data);
                double nextTask = simTime - timeFeed.getPreviousValue();

                timer.schedule(task(), Math.round(nextTask));
            }
        };
    }
}
