package com.redhat.patriot.generator;

import com.redhat.patriot.generator.device.Device;
import com.redhat.patriot.generator.device.dateFeed.DataFeed;
import com.redhat.patriot.generator.device.dateFeed.DayTemperatureDataFeed;
import com.redhat.patriot.generator.timeSimulation.TimeFeed;
import com.redhat.patriot.generator.timeSimulation.realTime.RealTimeSimulation;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * Created by jsmolar on 7/5/18.
 */
public class TestMain {

    public static void main(String[] args) {
        DataFeed dataFeed = new DayTemperatureDataFeed(13, 29);
        Device device = new Device(dataFeed);

        Expression calc = new ExpressionBuilder("abs(10000 * sin(y))")
            .variable("y")
            .build();
        TimeFeed feed = new TimeFeed(calc);
        RealTimeSimulation simulation = new RealTimeSimulation(device, feed);

        simulation.simulateData();
    }

}
