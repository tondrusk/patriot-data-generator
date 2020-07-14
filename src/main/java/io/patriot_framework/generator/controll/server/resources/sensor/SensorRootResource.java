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

package io.patriot_framework.generator.controll.server.resources.sensor;

import io.patriot_framework.generator.device.passive.sensors.Sensor;
import org.eclipse.californium.core.CoapResource;

/**
 * Root CoaP Resource for {@link Sensor} . Main responsibility is to create endpoint: /{#sensor.getLabel}.
 * This resource is used as a root element of the resource tree of Sensor.
 */
public class SensorRootResource extends CoapResource {

    public static final String NAME = "sensor";

    public SensorRootResource() {
        super(NAME);
    }
}
