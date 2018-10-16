package com.redhat.patriot.generator;

import com.redhat.patriot.generator.dataFeed.DataFeed;
import com.redhat.patriot.generator.device.active.AbstractActiveDevice;
import com.redhat.patriot.generator.dataFeed.NormalDistributionDataFeed;
import com.redhat.patriot.generator.timeSimulation.timeFeed.LinearTimeFeed;
import com.redhat.patriot.generator.timeSimulation.timeFeed.TimeFeed;

import java.util.ArrayList;

/**
 * Created by jsmolar on 7/5/18.
 */
public class TestMain {

    public static void main(String[] args) {
        DataFeed dataFeed = new NormalDistributionDataFeed(0, 1);
        TimeFeed feed = new LinearTimeFeed(2000);
        AbstractActiveDevice device = new AbstractActiveDevice();
        device.setDataFeed(dataFeed);
        device.setTimeFeed(feed);

        device.simulate();
    }

}
