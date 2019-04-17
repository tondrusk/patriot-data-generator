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

package io.patriot_framework.generator.controll;

import io.patriot_framework.generator.controll.resources.DeviceResource;
import io.patriot_framework.generator.device.passive.DataProducer;
import org.eclipse.californium.core.CoapResource;

public class SensorCoapController implements CoapController {

    private CoapDeviceControlServer server;

    private DataProducer sensor;

    private DeviceResource resource = null;

    public SensorCoapController(DataProducer sensor) {
        this.sensor = sensor;
        server = CoapDeviceControlServer.getInstance();
//        sensor.addDataFeed(new ConstantDataFeed(10000);
    }

    @Override
    public void registerDevice() {
        server.add(createResource());
    }

    @Override
    public void removeDevice() {
        server.remove(resource);
        resource = null;
    }

    private CoapResource createResource() {
        if(resource == null) {
            resource = new DeviceResource(sensor);
        }

        return resource;
    }

}
