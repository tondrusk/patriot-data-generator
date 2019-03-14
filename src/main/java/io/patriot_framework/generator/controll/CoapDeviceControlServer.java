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

public class CoapDeviceControlServer extends CoapServer {

//    private static final int COAP_PORT = NetworkConfig.getStandard().getInt(NetworkConfig.Keys.COAP_PORT);
//
//    private List<Device> register = new ArrayList<>();
//
//    public void registerDevice(Device device) {
//        register.add(device);
//        CoapResource res = new DataFeedResource();
//    }
//
//    public CoapDeviceControlServer() throws SocketException {
//
//        // provide an instance of a Hello-World resource
//        add(new DataFeedResource());
//    }
//
//    public void addEndpoints() {
//        for (InetAddress addr : EndpointManager.getEndpointManager().getNetworkInterfaces()) {
//            // only binds to IPv4 addresses and localhost
//            if (addr instanceof Inet4Address || addr.isLoopbackAddress()) {
//                InetSocketAddress bindToAddress = new InetSocketAddress(addr, COAP_PORT);
//                addEndpoint(new CoapEndpoint(bindToAddress));
//            }
//        }
//    }
//
//    /*
//     * Definition of the Hello-World Resource
//     */
//    class DataFeedResource extends CoapResource {
//
//        private String deviceLabel;
//
//
//        public DataFeedResource() {
//            this.deviceLabel = deviceLabel;
//            // set resource identifier
//            super("dataFeed");
//
//            // set display name
//            getAttributes().setTitle("Hello-World Resource");
//        }
//
//        @Override
//        public void handleGET(CoapExchange exchange) {
//
//            // respond to the request
//            exchange.respond("Hello World!");
//        }
//
//        @Override
//        public void handlePOST(CoapExchange exchange) {
//            exchange.accept();
////
//
////            exchange.respond(ResponseCode.CREATED);
//        }
//    }
}
