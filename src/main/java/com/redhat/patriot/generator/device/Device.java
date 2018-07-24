package com.redhat.patriot.generator.device;

import java.util.ArrayList;
import java.util.List;

import net.objecthunter.exp4j.Expression;

/**
 * Created by jsmolar on 7/3/18.
 */
public class Device {

    private String name;

    private DataFeed dataFeed;

    private Data data;

    public Device(double lambda) {
        this.dataFeed = new DataFeed(lambda);
//        setObserver();
    }

    public Device(Expression expression) {
        this.dataFeed = new DataFeed(expression);
    }

    public Data generateValue() {
        data.setValue(getSingleRandomValue());
//        data.setReady();

        return data;
    }

    public Double getSingleRandomValue() {
        return dataFeed.getValue();
    }

    public List<Double> getNRandomValues(int n) {
        List<Double> result = new ArrayList<>();

        for(int i = 0; i < n; i++) {
            result.add(dataFeed.getValue());
        }
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    private void setObserver() {
//        data = new Data();
//        DataObserver observer = new DataObserver();
//        data.addObserver(observer);
//    }

}
