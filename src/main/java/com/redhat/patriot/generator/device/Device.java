/*
 * Copyright 2018 Patriot project
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.redhat.patriot.generator.device;

import com.redhat.patriot.generator.device.dateFeed.DataFeed;
import com.redhat.patriot.generator.wrappers.JSONWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jsmolar on 7/3/18.
 */
public class Device {

    private static final Logger LOGGER = LoggerFactory.getLogger(Device.class);

    private String name;

    private DataFeed dataFeed;

    public Device() {
    }

    public Device(DataFeed dataFeed) {
        this.dataFeed = dataFeed;
    }

    public void simulateForTime(double time) {
        new JSONWrapper(getSingleRandomValue(time)).send();
    }

    public Data getSingleRandomValue(double time) {
        Data data = new Data();
        data.setDeviceName(name);
        data.setValue(dataFeed.getValue(time));

        return data;
    }

//    public List<Double> getNRandomValues(int n) {
//        List<Double> result = new ArrayList<>();
//
//        for (int i = 0; i < n; i++) {
//            result.add(dataFeed.getValue());
//        }
//        return result;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataFeed getDataFeed() {
        return dataFeed;
    }

    public void setDataFeed(DataFeed dataFeed) {
        this.dataFeed = dataFeed;
    }
}
