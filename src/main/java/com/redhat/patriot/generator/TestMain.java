package com.redhat.patriot.generator;

import com.redhat.patriot.generator.dataFeed.DataFeed;
import com.redhat.patriot.generator.dataFeed.LinearDataFeed;
import com.redhat.patriot.generator.dataFeed.NormalDistributionDataFeed;
import com.redhat.patriot.generator.device.Device;
import com.redhat.patriot.generator.basicDevices.Thermometer;
import com.redhat.patriot.generator.device.timeSimulation.TimeSimulation;
import com.redhat.patriot.generator.device.timeSimulation.TimeSimulationImpl;


/**
 * Created by jsmolar on 7/5/18.
 */
public class TestMain {

    public static void main(String[] args) {
        DataFeed dataFeed = new NormalDistributionDataFeed(0, 1);
        DataFeed timeFeed = new LinearDataFeed(2000);
        Device device = new Thermometer("TestHygrometer", dataFeed);

        TimeSimulation simT = new TimeSimulationImpl(timeFeed, device);
        simT.simulate();

        DataListener dl = new DataListener();
        dl.addDevice(device);
        Watcher w = new Watcher();
        dl.addObserver(w);

//        sample.simulate();
//        Consumer consumer = new Consumer(DataQueue.getInstance());
//        new Thread(consumer).start();
    }

}
