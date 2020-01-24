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

package io.patriot_framework.generator.controll.server.resources.device;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.patriot_framework.generator.dataFeed.DataFeed;
import io.patriot_framework.generator.dataFeed.DataFeedBean;
import io.patriot_framework.generator.device.passive.sensors.Sensor;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.io.IOException;
import java.util.List;

/**
 * Children resource of {@link SensorResource}. It handles all requests
 * aimed for {@link DataFeed} control.
 */
public class DataFeedResource extends CoapResource {

    private Sensor sensor;

    private ObjectMapper mapper;

    /**
     * Creates new resource with name: dataFeed
     *
     * @param sensor instance of Sensor
     */
    public DataFeedResource(Sensor sensor) {
        super("dataFeed");
        this.sensor = sensor;

        JsonFactory factory = new JsonFactory();
        mapper = new ObjectMapper(factory);
    }

    /**
     * Method returns information about DataFeed
     *
     * @param exchange the CoapExchange for the simple API
     */
    @Override
    public void handleGET(CoapExchange exchange) {
        List<DataFeed> df = sensor.getDataFeeds();
        exchange.respond(df.toString());
    }

    /**
     * Method should get new DataFeed in JSON format and add it to the list of DataFeeds
     * for particular sensor.
     *
     * @param exchange the CoapExchange for the simple API
     */
    @Override
    public void handlePOST(CoapExchange exchange) {
        exchange.accept();
        String body = exchange.getRequestText();

        DataFeedBean dataFeed = null;
        try {
            dataFeed = mapper.readValue(body, DataFeedBean.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        sensor.addDataFeed(dataFeed.getDataFeed());

        exchange.respond(CoAP.ResponseCode.CHANGED);
    }

}
