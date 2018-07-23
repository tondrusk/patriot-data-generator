package com.redhat.patriot.generator;

import com.redhat.patriot.generator.device.Device;
import com.redhat.patriot.generator.timeSimulation.TimeSimulation;
import com.redhat.patriot.generator.timeSimulation.realTimeSimulation.RealTimeSimulation;
import com.redhat.patriot.generator.timeSimulation.realTimeSimulation.TimeFrame;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * Created by jsmolar on 7/5/18.
 */
public class TestMain {

    public static void main(String[] args) {
        Device device = new Device(50);
        device.poissonDist();
        TimeSimulation timeSimulation = new TimeSimulation(device);

        Expression calc = new ExpressionBuilder("abs(3 * sin(y))")
            .variable("y")
            .build();
        TimeFrame frame = new TimeFrame(calc);
        RealTimeSimulation simulation = new RealTimeSimulation(device, frame);

        simulation.simulateData();

//        timeSimulation.simDataInTime(10000);
    }

}

//1. zabalit data (json)
//2. casovac (aplikacie bezi a posiela data)
//3. data generovane na zaklade funkcie
