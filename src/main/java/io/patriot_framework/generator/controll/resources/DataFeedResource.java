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

package io.patriot_framework.generator.controll.resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.patriot_framework.generator.dataFeed.DataFeed;
import io.patriot_framework.generator.device.passive.DataProducer;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.io.IOException;


public class DataFeedResource<E> extends CoapResource {

    private DataProducer<E> sensor;

    private ObjectMapper mapper;

    public DataFeedResource(DataProducer sensor) {
        super("dataFeed");
        this.sensor = sensor;

        JsonFactory factory = new JsonFactory();
        mapper = new ObjectMapper(factory);
    }

    @Override
    public void handleGET(CoapExchange exchange) {

        // respond to the request
        exchange.respond("aa");
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        exchange.accept();
        String body = exchange.getRequestText();

        DataFeed dataFeed = null;
        try {
            dataFeed = mapper.readValue(body, new TypeReference<DataFeed>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        sensor.addDataFeed(dataFeed);

        exchange.respond(CoAP.ResponseCode.CHANGED);
    }

}
