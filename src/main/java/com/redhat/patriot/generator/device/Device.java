package com.redhat.patriot.generator.device;

import static umontreal.ssj.rng.MRG32k3a.setPackageSeed;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import umontreal.ssj.probdist.PoissonDist;
import umontreal.ssj.randvar.PoissonGen;
import umontreal.ssj.randvar.RandomVariateGen;
import umontreal.ssj.randvar.RandomVariateGenInt;
import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.rng.RandomStream;

/**
 * Created by jsmolar on 7/3/18.
 */
public class Device {

    private String name;

    private double lambda;

    private RandomVariateGen randomValues = null;

    private static final DataEncoding encoding = DataEncoding.JSON;

    public Device(double lambda) {
        this.lambda = lambda;
    }

    public void poissonDist() {
        long[] seed = {1L , 2L, 3L, 4L, 5L, 6L};
        MRG32k3a rng = new MRG32k3a();
        setPackageSeed(seed);

        RandomStream streamDemand = rng;
        rng.resetStartStream();

        RandomVariateGenInt genDemand = new PoissonGen(streamDemand, new PoissonDist(lambda));
        randomValues = genDemand;
    }

    public String generateValue() {
        JSONObject data = new JSONObject();
        data.put("name", name)
            .put("data", getSingleRandomValue());

        return data.toString();
    }

    public Double getSingleRandomValue() {
        return randomValues.nextDouble();
    }

    public List<Double> getNRandomValues(int n) {
        List<Double> result = new ArrayList<>();

        for(int i = 0; i < n; i++) {
            result.add(randomValues.nextDouble());
        }
        return result;
    }

}
