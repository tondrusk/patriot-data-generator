package com.redhat.patriot.generator.timeSimulation;

import java.util.ArrayList;
import java.util.List;

import com.redhat.patriot.generator.device.Data;
import com.redhat.patriot.generator.device.Device;
import umontreal.ssj.randvar.BinomialConvolutionGen;
import umontreal.ssj.randvar.ErlangConvolutionGen;
import umontreal.ssj.randvar.RandomVariateGen;
import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.simevents.Event;

/**
 * Created by jsmolar on 7/5/18.
 */
public class DataEvent extends Event {

    private Device device;

    private Boolean repeat = false;

    private List<Data> simulatedData = new ArrayList<>();

    private RandomVariateGen timeGeneration = new ErlangConvolutionGen(new MRG32k3a(), 2);

    public DataEvent(Device device, Boolean repeat) {
        this.device = device;
        device.poissonDist();
        this.repeat = repeat;
    }

    public void actions() {
        if(repeat) {
            this.schedule(timeGeneration.nextDouble());
        }

        Data data = new Data(this.time(), device.getSingleRandomValue());

        simulatedData.add(data);
    }

    public void binomialConvolution() {
        timeGeneration = new BinomialConvolutionGen(new MRG32k3a(), 2, 60.0);
    }

    public List<Data> getSimulatedData() {
        return simulatedData;
    }

}
