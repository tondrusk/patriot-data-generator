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

import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.EndpointManager;
import org.eclipse.californium.core.network.config.NetworkConfig;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class CoapDeviceControlServer extends CoapServer {

    private static final int COAP_PORT = NetworkConfig.getStandard().getInt(NetworkConfig.Keys.COAP_PORT);

    private static CoapDeviceControlServer singleInstance;

    private boolean isRunning = false;

    private int nodesCount = 0;

    private CoapDeviceControlServer() throws SocketException {
        start();
        addEndpoints();
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

//    @Override
//    public CoapServer add(Resource... resources) {
//        if (!isRunning) {
//            start();
//            addEndpoints();
//        }
//        nodesCount++;
//
//        return super.add(resources);
//    }
//
//    @Override
//    public boolean remove(Resource resource) {
//        boolean removed = super.remove(resource);
//        nodesCount--;
//
//        if (isRunning && nodesCount < 1) {
//            stop();
//        }
//
//        return removed;
//    }

    private void addEndpoints() {
        for (InetAddress addr : EndpointManager.getEndpointManager().getNetworkInterfaces()) {
            // only binds to IPv4 addresses and localhost
            if (addr instanceof Inet4Address || addr.isLoopbackAddress()) {
                InetSocketAddress bindToAddress = new InetSocketAddress(addr, COAP_PORT);
                addEndpoint(new CoapEndpoint(bindToAddress));
            }
        }
    }

}
