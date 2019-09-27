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

package io.patriot_framework.builders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import io.patriot_framework.beans.ActiveDeviceBean;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class DeviceYamlBuilder {

    private List<ActiveDeviceBean> activeDeviceBeans;

    private String fileName = "device.yml";

    public DeviceYamlBuilder loadDevices() throws IOException {
        YAMLFactory yamlFactory = new YAMLFactory();
        yamlFactory.disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);

        ObjectMapper mapper = new ObjectMapper(yamlFactory);

        ClassLoader classLoader = this.getClass().getClassLoader();
        File loadedFile = new File(classLoader.getResource(fileName).getFile());

        activeDeviceBeans = mapper.readValue(loadedFile, new TypeReference<List<ActiveDeviceBean>>(){});

        return this;
    }

    public void startActiveDevices() {
        for(ActiveDeviceBean device : activeDeviceBeans) {
            device.getDevice().start();
        }
    }


}
