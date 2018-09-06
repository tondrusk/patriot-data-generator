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

package com.redhat.patriot.generator.timeSimulation.realTime;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.redhat.patriot.generator.device.Device;
import com.redhat.patriot.generator.timeSimulation.TimeFeed;

/**
 * Created by jsmolar on 7/17/18.
 */
public class RealTimeSimulation {

    private Device device;

    private TimeFeed timeFeed;

    private Timer timer;

    public RealTimeSimulation(Device device, TimeFeed timeFeed) {
        this.device = device;
        this.timeFeed = timeFeed;
        timer = new Timer();
    }

    public void simulateData() {
        timer.schedule(initTask(), 0);
    }

    public void stopSimulation() {
        timer.cancel();
        timer.purge();
    }

    private TimerTask initTask() {
        return new TimerTask() {
            @Override
            public void run() {
                timer.schedule(task(), Math.round(timeFeed.getTime()));
            }
        };
    }

    private TimerTask task() {
        return new TimerTask() {
            @Override
            public void run() {
                System.out.println(new Date(System.currentTimeMillis()).toString());
                System.out.println("Thread: " + Thread.currentThread().getId());
                device.simulate();
                timer.schedule(task(), Math.round(timeFeed.getTime()));
            }
        };
    }

}
