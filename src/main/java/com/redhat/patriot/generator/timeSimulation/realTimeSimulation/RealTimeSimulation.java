package com.redhat.patriot.generator.timeSimulation.realTimeSimulation;

import java.util.Timer;
import java.util.TimerTask;

import com.redhat.patriot.generator.device.Device;

/**
 * Created by jsmolar on 7/17/18.
 */
public class RealTimeSimulation {

    private Device device;

    private TimeFrame timeFrame;

    private Timer timer;

    public RealTimeSimulation(Device device, TimeFrame timeFrame) {
        this.device = device;
        this.timeFrame = timeFrame;
        timer = new Timer();
    }

    public void simulateData() {
        while (true) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println(device.generateValue());
                }
            }, Math.round(timeFrame.getTime()));
        }
    }

    public void stopSimulation() {
        timer.cancel();
        timer.purge();
    }

}
