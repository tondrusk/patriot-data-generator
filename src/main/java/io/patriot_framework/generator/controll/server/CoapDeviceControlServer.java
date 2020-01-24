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

package io.patriot_framework.generator.controll.server;

import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.EndpointManager;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.core.server.resources.Resource;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * All Devices should be handled by one CoapServer to be able to guarantee consistent Resource tree.
 * CoapDeviceControlServer class extends {@link CoapServer} with functionality
 * for Device resource administration.
 *
 * One server manages all devices resources, therefore it is design as singleton.
 * It maintains functionality of {@link CoapServer} with one small difference:
 * instance contains counter (registeredDevices) for registered resources.
 * If server has no active resources registered it stops, otherwise it should be up and running.
 */
public class CoapDeviceControlServer extends CoapServer {

    private static final int COAP_PORT = NetworkConfig.getStandard().getInt(NetworkConfig.Keys.COAP_PORT);

    private static CoapDeviceControlServer singleInstance;

    /**
     * Contains number of registered resources. This variable is used to determinate
     * if server should be running (registeredDevices > 0) or should be stopped (registeredDevices =0)
     */
    private int registeredDevices = 0;

    private CoapDeviceControlServer() {
        addEndpoints();
    }

    public static CoapDeviceControlServer getInstance() {
        if (singleInstance == null) {
            singleInstance = new CoapDeviceControlServer();
        }

        return singleInstance;
    }

    /**
     * Adds {@link Resource}s to the resource tree.
     *
     * @param resources one or more {@link Resource}s
     * @return CoapServer instance
     */
    @Override
    public CoapServer add(Resource... resources) {
        super.add(resources);
        registeredDevices++;
        toggleServer();

        return this;
    }

    /**
     * Removes {@link Resource} from the resource tree.
     *
     * @param resource one {@link Resource}
     * @return CoapServer instance
     */
    @Override
    public boolean remove(Resource resource) {
        boolean removed = super.remove(resource);
        if(removed)
            registeredDevices--;

        toggleServer();

        return removed;
    }

    /**
     * Simple control of registered resources, to manage server status.
     */
    private void toggleServer(){
        if (registeredDevices > 0) {
            start();
        } else {
            stop();
        }
    }

    /**
     * Add individual endpoints listening on default CoAP port on all IPv4 addresses of all network interfaces.
     *
     * Note: this method will be changed to expose endpoints required by Patriot framework
     */
    private void addEndpoints() {
        NetworkConfig config = NetworkConfig.getStandard();
        for (InetAddress addr : EndpointManager.getEndpointManager().getNetworkInterfaces()) {
            InetSocketAddress bindToAddress = new InetSocketAddress(addr, COAP_PORT);
                CoapEndpoint.Builder builder = new CoapEndpoint.Builder();
                builder.setInetSocketAddress(bindToAddress);
                builder.setNetworkConfig(config);
                addEndpoint(builder.build());

        }
    }

}
