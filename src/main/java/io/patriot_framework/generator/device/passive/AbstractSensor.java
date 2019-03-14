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
import io.patriot_framework.generator.converter.DataConverter;
import io.patriot_framework.generator.dataFeed.DataFeed;
import io.patriot_framework.generator.device.AbstractDevice;
import io.patriot_framework.generator.network.NetworkAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Abstract class for Sensor - device with single DataFeed.
 *
 * @param <E> type of generated data
 * @param <T> type of object with which DataFeed operates
 */
public abstract class AbstractSensor<E,T> extends AbstractDevice implements Sensor<T> {

    private DataConverter<E,T> dataConverter;

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "className")
    private DataFeed<T> dataFeed;

    private Class<E> outputType;
    private Class<T> inputType;

    @JsonCreator
    public AbstractSensor(@JsonProperty("label") String label) {
        super(label);
    }

    public AbstractSensor(String label, DataFeed<T> dataFeed) {
        super(label);
        this.dataFeed = dataFeed;

//        if (!inputType.isAssignableFrom(outputType)) {
//            throw new IllegalArgumentException("DataFeed type is not castable to Sensors type");
//        }
    }

    @Override
    public List<E> requestData(Object... param) {
        if(dataFeed == null) {
            throw new IllegalArgumentException("Data Feed cannot be null");
        }

        T newData = dataFeed.getNextValue(param);
        E result = dataConverter.transform(newData);

        if(getNetworkAdapter() != null) {
            sendData(result);
        }

        return Collections.singletonList(result);
    }

    private void sendData(E data) {
        String dw = getDataWrapper().wrapData(this, data);
        NetworkAdapter networkAdapter = getNetworkAdapter();
        if(networkAdapter != null) {
            networkAdapter.send(dw);
        }
    }

    @Override
    public void setDataFeed(DataFeed<T> dataFeed) {
        this.dataFeed = dataFeed;
    }

    @Override
    public DataFeed<T> getDataFeed() {
        return dataFeed;
    }

    public DataConverter<E, T> getDataConverter() {
        return dataConverter;
    }

    public void setDataConverter(DataConverter<E, T> dataConverter) {
        this.dataConverter = dataConverter;
    }
}
