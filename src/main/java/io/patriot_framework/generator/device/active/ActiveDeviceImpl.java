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

package io.patriot_framework.generator.device.active;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.patriot_framework.generator.dataFeed.DataFeed;
import io.patriot_framework.generator.dataFeed.Seed;
import io.patriot_framework.generator.device.Device;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

public class ActiveDeviceImpl implements ActiveDevice {

    private static final Logger log = LogManager.getLogger(Seed.class);

    private Timer timer = new Timer();

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "className")
    private DataFeed<Double> timeFeed;

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "className")
    private Device device;

    public ActiveDeviceImpl() {
    }

    public ActiveDeviceImpl(DataFeed<Double> timeFeed, Device device) {
        this.timeFeed = timeFeed;
        this.device = device;
    }

    @Override
    public void startSimulation() {
        timer.schedule(task(), 0);
        log.info("Device is started");
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

                device.requestData(simTime);

                double nextTask = simTime - timeFeed.getPreviousValue();

                timer.schedule(task(), Math.round(nextTask));
            }
        };
    }

    @Override
    public void setDevice(Device device) {
        this.device = device;
    }

    @Override
    public Device getDevice() {
        return device;
    }

    @Override
    public void setTimeFeed(DataFeed<Double> timeFeed) {
        this.timeFeed = timeFeed;
    }

    @Override
    public DataFeed<Double> getTimeFeed() {
        return timeFeed;
    }

}
