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
                System.out.println(new Date(System.currentTimeMillis()).toString() + " - " + device.getSingleRandomValue());
                timer.schedule(task(), Math.round(timeFeed.getTime()));
            }
        };
    }

}
