/*
 * Copyright 2020 Patriot project
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

package io.patriot_framework.generator.controll.client;

import io.patriot_framework.generator.controll.server.resources.device.SensorResource;
import org.eclipse.californium.elements.exception.ConnectorException;

import java.io.IOException;
import java.util.Set;
import java.util.regex.Pattern;

public class ClientResourceHandler {

    private Set<String> deviceEndpoints;

    private CoapControlClient ccc;

    private String deviceLabel;

    public ClientResourceHandler(CoapControlClient ccc, Set<String> deviceEndpoints, String deviceLabel) {
        this.deviceEndpoints = deviceEndpoints;
        this.ccc = ccc;
        this.deviceLabel = deviceLabel;
    }

    public void toggleDevice() throws ConnectorException, IOException {
        Pattern pattern = Pattern.compile(String.format(SensorResource.PATTERN, deviceLabel));

        String toggleEndpoint = deviceEndpoints
                .stream()
                .filter(pattern.asPredicate())
                .findFirst()
                .orElse(null);

        //TODO: throw exception if null

        ccc.post(toggleEndpoint, null);
    }

}
