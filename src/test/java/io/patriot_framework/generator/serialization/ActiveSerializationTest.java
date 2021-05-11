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

package io.patriot_framework.generator.serialization;

import io.patriot_framework.generator.dataFeed.ConstantDataFeed;
import io.patriot_framework.generator.dataFeed.DataFeed;
import io.patriot_framework.generator.dataFeed.NormalDistVariateDataFeed;
import io.patriot_framework.generator.device.Device;
import io.patriot_framework.generator.device.active.Active;
import io.patriot_framework.generator.device.active.ActiveDevice;
import io.patriot_framework.generator.device.impl.basicSensors.Thermometer;
import io.patriot_framework.generator.network.NetworkAdapter;
import io.patriot_framework.generator.network.Rest;
import io.patriot_framework.generator.network.wrappers.JSONWrapper;
import io.patriot_framework.generator.utils.JSONSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadis</a>
 */
public class ActiveSerializationTest {
    private File file;

    @BeforeEach
    void setup() throws IOException {
        file = File.createTempFile("thermometer", ".json");
        file.deleteOnExit();
    }


    @Test
    void activeDevice() {
        DataFeed df = new NormalDistVariateDataFeed(18, 2);
        Device temperature = new Thermometer("activeThermometer", df);
        NetworkAdapter na = new Rest("https://httpbin.org/post", new JSONWrapper());
        temperature.setNetworkAdapter(na);
        DataFeed tf = new ConstantDataFeed(2000);
        Active activeDevice = new ActiveDevice(temperature, tf);


        JSONSerializer.serialize(activeDevice, file);
        Active another = JSONSerializer.deserializeActiveDevice(file);
        assert (activeDevice.equals(another));
    }
}
