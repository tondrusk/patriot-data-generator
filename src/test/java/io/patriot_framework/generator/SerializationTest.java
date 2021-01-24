/*
 * Copyright 2021 Patriot project
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

package io.patriot_framework.generator;

import io.patriot_framework.generator.dataFeed.DataFeed;
import io.patriot_framework.generator.device.impl.basicActuators.RotaryActuator;
import io.patriot_framework.generator.device.passive.actuators.Actuator;
import io.patriot_framework.generator.device.passive.sensors.Sensor;
import io.patriot_framework.generator.network.NetworkAdapter;
import io.patriot_framework.generator.network.Rest;
import io.patriot_framework.generator.network.wrappers.JSONWrapper;
import io.patriot_framework.generator.utils.JSONSerializer;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public class SerializationTest {
    @Test
    void serialize()
    {
        DataFeed temperature = new ExponentialDistDataFeed(0.02);
        DataFeed humidity = new NormalDistVariateDataFeed(30, 7);
        Sensor sensor = new DHT11("dht11", temperature, humidity);
        NetworkAdapter na = new Rest(httpEndpoint, new JSONWrapper());
        sensor.setNetworkAdapter(na);
        JSONSerializer.serialize(actuator, new File("test.json"));
        System.out.println("bla");
    }
}
