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
import io.patriot_framework.generator.dataFeed.ExponentialDistDataFeed;
import io.patriot_framework.generator.dataFeed.NormalDistVariateDataFeed;
import io.patriot_framework.generator.device.Device;
import io.patriot_framework.generator.device.impl.basicSensors.DHT11;
import io.patriot_framework.generator.device.impl.basicSensors.Thermometer;
import io.patriot_framework.generator.device.passive.sensors.Sensor;
import io.patriot_framework.generator.network.NetworkAdapter;
import io.patriot_framework.generator.network.Rest;
import io.patriot_framework.generator.network.wrappers.JSONWrapper;
import io.patriot_framework.generator.utils.JSONSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadiš</a>
 */
public class SerializationTest {
    private File file;

    @BeforeEach
    void setup() throws IOException {
        file = File.createTempFile("thermometer", ".json");
        file.deleteOnExit();
    }

    @Test
    void serializeThermometer() {
        DataFeed df = new NormalDistVariateDataFeed(18, 2);
        NetworkAdapter na = new Rest("https://httpbin.org/post", new JSONWrapper());
        Device sensor = new Thermometer("simpleThermometer", df);
        sensor.setNetworkAdapter(na);

        JSONSerializer.serialize(sensor, file);
        Device another = JSONSerializer.deserializeDevice(file);
        assert (another.equals(sensor));
    }

    @Test
    void serializeDHT11(){
        DataFeed temperature = new ExponentialDistDataFeed(0.02);
        DataFeed humidity = new NormalDistVariateDataFeed(30, 7);
        Device sensor = new DHT11("dht11", temperature, humidity);
        NetworkAdapter na = new Rest("https://httpbin.org/post", new JSONWrapper());
        sensor.setNetworkAdapter(na);

        JSONSerializer.serialize(sensor, file);
        Device another = JSONSerializer.deserializeDevice(file);
        assert (another.equals(sensor));
    }
}
