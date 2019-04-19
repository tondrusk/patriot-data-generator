/*
 * Copyright 2019 Patriot project
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

package io.patriot_framework.generator.device.passive;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.patriot_framework.generator.Data;
import io.patriot_framework.generator.controll.CoapController;
import io.patriot_framework.generator.controll.SensorCoapController;
import io.patriot_framework.generator.converter.DataConverter;
import io.patriot_framework.generator.dataFeed.DataFeed;
import io.patriot_framework.generator.device.AbstractDevice;
import io.patriot_framework.generator.network.NetworkAdapter;
import org.eclipse.californium.core.CoapServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * Abstract class for Sensor - device with single DataFeed.
 *
 * @param <E> type of generated data
 * @param <T> type of object with which DataFeed operates
 */
public abstract class AbstractSingleSensor extends AbstractDevice implements SingleSensor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSingleSensor.class);

    private DataConverter dataConverter;

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "className")
    private DataFeed dataFeed;

//    private Class<E> outputType;
//    private Class<T> inputType;

    @JsonCreator
    public AbstractSingleSensor(@JsonProperty("label") String label) {
        super(label);
    }

    public AbstractSingleSensor(String label, DataFeed dataFeed) {
        super(label);
        this.dataFeed = dataFeed;
//        CoapController cc = new SensorCoapController<T>(this);
//        setCoapController();

//        if (!inputType.isAssignableFrom(outputType)) {
//            throw new IllegalArgumentException("DataFeed type is not castable to Sensors type");
//        }
    }

    @Override
    public List<Data> requestData(Object... param) {
        if(dataFeed == null) {
            throw new IllegalArgumentException("Data Feed cannot be null");
        }

        Data newData = dataFeed.getNextValue(param);

//        if(getNetworkAdapter() != null) {
//            sendData(result);
//        }

        LOGGER.info(this.toString() + " new data: " + newData.toString());

        return Collections.singletonList(newData);
    }

//    private void sendData(E data) {
//        String dw = getDataWrapper().wrapData(this, data);
//        NetworkAdapter networkAdapter = getNetworkAdapter();
//        if(networkAdapter != null) {
//            networkAdapter.send(dw);
//        }
//    }

    @Override
    public void setDataFeed(DataFeed dataFeed) {
        this.dataFeed = dataFeed;
    }

    @Override
    public List<DataFeed> getDataFeed() {
        return Collections.singletonList(dataFeed);
    }

    @Override
    public void addDataFeed(DataFeed dataFeed) {
        this.dataFeed = dataFeed;
    }

    @Override
    public void removeDataFeed(DataFeed dataFeed) {
        this.dataFeed = null;
    }

//    public DataConverter<E, T> getDataConverter() {
//        return dataConverter;
//    }

//    public void setDataConverter(DataConverter<E, T> dataConverter) {
//        this.dataConverter = dataConverter;
//    }

}
