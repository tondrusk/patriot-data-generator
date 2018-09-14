package com.redhat.patriot.generator;

import static java.util.concurrent.TimeUnit.DAYS;

import com.redhat.patriot.generator.device.Device;
import com.redhat.patriot.generator.device.dateFeed.DataFeed;
import com.redhat.patriot.generator.device.dateFeed.DayTemperatureDataFeed;
import com.redhat.patriot.generator.timeSimulation.realTime.RealTimeSimulation;
import com.redhat.patriot.generator.timeSimulation.timeFeed.LinearTimeFeed;
import com.redhat.patriot.generator.timeSimulation.timeFeed.TimeFeed;

/**
 * Created by jsmolar on 7/5/18.
 */
public class TestMain {

    public static void main(String[] args) {
        DataFeed dataFeed = new DayTemperatureDataFeed(13, 29);
        Device device = new Device(dataFeed);

        TimeFeed feed = new LinearTimeFeed(DAYS.toMillis(1));
        RealTimeSimulation simulation = new RealTimeSimulation(device, feed);
        RealTimeSimulation simulation1 = new RealTimeSimulation(device, feed);

        simulation.simulateData();
        simulation1.simulateData();
    }

}
