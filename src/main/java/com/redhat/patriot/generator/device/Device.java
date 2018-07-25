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

    private DataEncoding dataEncoding = DataEncoding.JSON;

    public Device(double lambda) {
        this.dataFeed = new DataFeed(lambda);
    }

    public Device(Expression expression) {
        this.dataFeed = new DataFeed(expression);
    }
    
    public Data getSingleRandomValue() {
        Data data = new Data();
        data.setValue(dataFeed.getValue());

        return data;
    }

    public List<Double> getNRandomValues(int n) {
        List<Double> result = new ArrayList<>();

        for (int i = 0; i < n; i++) {
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

    public DataEncoding getDataEncoding() {
        return dataEncoding;
    }

    public void setDataEncoding(DataEncoding dataEncoding) {
        this.dataEncoding = dataEncoding;
    }
}
