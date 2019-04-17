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

import io.patriot_framework.generator.controll.resources.ActuatorResource;
import io.patriot_framework.generator.controll.resources.DeviceResource;
import io.patriot_framework.generator.device.passive.Actuator;
import io.patriot_framework.generator.device.passive.DataProducer;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.EndpointManager;
import org.eclipse.californium.core.network.config.NetworkConfig;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class CoapDeviceControlServer extends CoapServer {

    private static final int COAP_PORT = NetworkConfig.getStandard().getInt(NetworkConfig.Keys.COAP_PORT);

    private static CoapDeviceControlServer singleInstance;

    private Map<String,DataProducer> sensorRegister = new HashMap<>();
    private Map<String,Actuator> actuatorRegister = new HashMap<>();

    private CoapDeviceControlServer() throws SocketException {

    }

    public static CoapDeviceControlServer getInstance() {
        if (singleInstance == null) {
            try {
                singleInstance = new CoapDeviceControlServer();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }

        return singleInstance;
    }

    public void registerSensor(DataProducer sensor) {
        sensorRegister.put(sensor.getLabel(), sensor);

        add(new DeviceResource(sensor));
    }

    public void registerActuator(Actuator actuator) {
        actuatorRegister.put(actuator.getLabel(), actuator);

        add(new ActuatorResource(actuator));
    }

    public void addEndpoints() {
        for (InetAddress addr : EndpointManager.getEndpointManager().getNetworkInterfaces()) {
            // only binds to IPv4 addresses and localhost
            if (addr instanceof Inet4Address || addr.isLoopbackAddress()) {
                InetSocketAddress bindToAddress = new InetSocketAddress(addr, COAP_PORT);
                addEndpoint(new CoapEndpoint(bindToAddress));
            }
        }
    }

}
