package com.redhat.patriot.generator.timeSimulation;

import com.redhat.patriot.generator.device.Device;
import umontreal.ssj.randvar.ErlangConvolutionGen;
import umontreal.ssj.randvar.ErlangGen;
import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.simevents.Sim;

/**
 * Created by jsmolar on 7/5/18.
 */
public class TimeSimulation {

    private static final double minute = 1.0 / 60.0;

    private Device device;

    public TimeSimulation(Device device) {
        this.device = device;
    }

    public void simDataInTime(int time) {
        ErlangGen timeGeneration = new ErlangConvolutionGen(new MRG32k3a(), 2, 1.0 / minute);

        DataEvent dataEvent = new DataEvent(device, true);

        Sim.init();
        new EndOfSimulation().schedule(time);
        dataEvent.schedule(1);
        Sim.start();

        System.out.println(dataEvent.getSimulatedData());
    }

    public void simDataInRuns(int runs) {
        ErlangGen timeGeneration = new ErlangConvolutionGen(new MRG32k3a(), 2, 1.0 / minute);

        DataEvent dataEvent = new DataEvent(device, true);

        for(int i = 0; i < runs; i++) {
            Sim.init();
            dataEvent.schedule(timeGeneration.nextDouble());
            Sim.start();
        }
    }

}
