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

import com.redhat.patriot.generator.dataFeed.DataFeed;
import com.redhat.patriot.generator.events.DataQueue;
import com.redhat.patriot.generator.network.Rest;
import com.redhat.patriot.generator.wrappers.DataWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jsmolar on 7/3/18.
 */
public abstract class AbstractDevice implements Device {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractDevice.class);

    private String label;

    private DataFeed dataFeed;

    private Rest rest;

    private DataWrapper dataWrapper;

    private DataQueue dataQueue = DataQueue.getInstance();
    private boolean queuingEnabled = false;

    public AbstractDevice(String label) {
        this.label = label;
    }

    public AbstractDevice(String label, DataFeed dataFeed) {
        this.label = label;
        this.dataFeed = dataFeed;
    }

    @Override
    public double requestData(Object... param) {
        if(dataFeed == null) {
            throw new IllegalArgumentException("Data Feed cannot be null");
        }

        double newData = dataFeed.getNextValue(param);

        if(queuingEnabled) {
            dataQueue.add(newData);
        }
        sendData(newData);

        return newData;
    }

    private void sendData(double data) {
        if(rest != null) {
            new Rest("http://requestbin.fullcontact.com/17xt3l91").send(this, data); //rest
        }
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public DataFeed getDataFeed() {
        return dataFeed;
    }

    @Override
    public void setDataFeed(DataFeed dataFeed) {
        this.dataFeed = dataFeed;
    }

    @Override
    public boolean isQueuingEnabled() {
        return queuingEnabled;
    }

    @Override
    public void setQueuingEnabled(boolean queuingEnabled) {
        this.queuingEnabled = queuingEnabled;
    }

    @Override
    public DataWrapper getDataWrapper() {
        return dataWrapper;
    }

    @Override
    public void setDataWrapper(DataWrapper dataWrapper) {
        this.dataWrapper = dataWrapper;
    }
//
//    @Override
//    public abstract String getUnit();

}
