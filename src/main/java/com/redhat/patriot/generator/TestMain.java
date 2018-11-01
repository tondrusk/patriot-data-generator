package com.redhat.patriot.generator;

import com.redhat.patriot.generator.dataFeed.DataFeed;
import com.redhat.patriot.generator.device.Hygrometer;
import com.redhat.patriot.generator.device.active.AbstractActiveDevice;
import com.redhat.patriot.generator.dataFeed.NormalDistributionDataFeed;
import com.redhat.patriot.generator.dataFeed.LinearDataFeed;
import com.redhat.patriot.generator.device.active.ActiveDevice;


/**
 * Created by jsmolar on 7/5/18.
 */
public class TestMain {

    public static void main(String[] args) {
        DataFeed dataFeed = new NormalDistributionDataFeed(0, 1);
        DataFeed timeFeed = new LinearDataFeed(2000);
        ActiveDevice device = new Hygrometer("TestHygrometer", dataFeed, timeFeed);

        device.simulate();
    }

}
