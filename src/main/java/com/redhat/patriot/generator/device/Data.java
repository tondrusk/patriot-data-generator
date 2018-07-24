package com.redhat.patriot.generator.device;

import java.util.Observable;

/**
 * Created by jsmolar on 7/5/18.
 */
public class Data extends Observable {

    private double time;

    private double value;

    public Data() {
    }

    public Data(double time, double value) {
        this.time = time;
        this.value = value;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

//    public void setReady() {
//        setChanged();
//        notifyObservers();
//    }

}
