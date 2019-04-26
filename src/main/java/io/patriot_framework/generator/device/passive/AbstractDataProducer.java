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

import io.patriot_framework.generator.Data;
import io.patriot_framework.generator.controll.SensorCoapController;
import io.patriot_framework.generator.dataFeed.DataFeed;
import io.patriot_framework.generator.device.AbstractDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for device Composition - one unit with multiple DataFeeds
 */
public abstract class AbstractDataProducer extends AbstractDevice implements DataProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDataProducer.class);

//    private DataConverter transform;

    private List<DataFeed> dataFeed;

    public AbstractDataProducer(String label) {
        super(label);
        setCoapController(new SensorCoapController(this));

//        if (!inputType.isAssignableFrom(outputType)) {
//            throw new IllegalArgumentException("DataFeed type is not castable to Sensors type");
//        }
    }

    @Override
    public List<Data> requestData(Object... param) {
        List<Data> result = new ArrayList<>();
//        HashMap<String, Data> networkData = new HashMap<>();

        for(DataFeed df : dataFeed) {
            Data newData = df.getNextValue();
//            networkData.put(df.getLabel(), newValue);
            result.add(newData);
        }

//        if(getNetworkAdapter() != null) {
//            sendData(networkData);
//        }

        LOGGER.info(this.toString() + " new data: " + result.toString());

        return result;
    }

//    private void sendData(HashMap<String, E> data) {
//        String dw = getDataWrapper().wrapData(this, data);
//        NetworkAdapter networkAdapter = getNetworkAdapter();
//        if(networkAdapter != null) {
//            networkAdapter.send(dw);
//        }
//    }

    @Override
    public void addDataFeed(DataFeed dataFeed) {
        this.dataFeed.add(dataFeed);
    }

    @Override
    public void removeDataFeed(DataFeed dataFeed) {
        this.dataFeed.remove(dataFeed);
    }

    @Override
    public List<DataFeed> getDataFeed() {
        return dataFeed;
    }

//    public abstract void setDataConverter(DataConverter<E,T> dataConverter);

}
