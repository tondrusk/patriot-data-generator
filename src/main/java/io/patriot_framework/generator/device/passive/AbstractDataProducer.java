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

import io.patriot_framework.generator.converter.DataConverter;
import io.patriot_framework.generator.dataFeed.DataFeed;
import io.patriot_framework.generator.device.AbstractDevice;
import io.patriot_framework.generator.device.active.ActiveDevice;
import io.patriot_framework.generator.network.NetworkAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Abstract class for device Composition - one unit with multiple DataFeeds
 *
 * @param <E> type of generated data
 * @param <T> type of object with which all DataFeeds operate
 */
public abstract class AbstractDataProducer<E,T> extends AbstractDevice implements DataProducer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDataProducer.class);

    private ActiveDevice ts;

    private DataConverter<E,T> transform;

    private List<DataFeed<T>> dataFeed;

//    private Class<E> outputType;
//    private Class<T> inputType;

    public AbstractDataProducer(String label) {
        super(label);

//        if (!inputType.isAssignableFrom(outputType)) {
//            throw new IllegalArgumentException("DataFeed type is not castable to Sensors type");
//        }
    }

    @Override
    public List<E> requestData(Object... param) {
        List<E> result = new ArrayList<>();
        HashMap<String, E> networkData = new HashMap<>();

        for(DataFeed<T> df : dataFeed) {
            E newValue = transform.transform(df.getNextValue());
            networkData.put(df.getLabel(), newValue);
            result.add(newValue);
        }

        if(getNetworkAdapter() != null) {
            sendData(networkData);
        }

        LOGGER.info(this.toString() + " new data: " + result.toString());

        return result;
    }

    private void sendData(HashMap<String, E> data) {
        String dw = getDataWrapper().wrapData(this, data);
        NetworkAdapter networkAdapter = getNetworkAdapter();
        if(networkAdapter != null) {
            networkAdapter.send(dw);
        }
    }

    @Override
    public void addDataFeed(DataFeed<T> dataFeed) {
        this.dataFeed.add(dataFeed);
    }

    @Override
    public void removeDataFeed(DataFeed<T> dataFeed) {
        this.dataFeed.remove(dataFeed);
    }

    @Override
    public List<DataFeed<T>> getDataFeed() {
        return dataFeed;
    }

    public abstract void setDataConverter(DataConverter<E,T> dataConverter);

}
