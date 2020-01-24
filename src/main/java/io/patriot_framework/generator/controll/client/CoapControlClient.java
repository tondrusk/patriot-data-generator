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

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.WebLink;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.elements.exception.ConnectorException;

import java.io.IOException;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * CoaP Control Client for Data Generator Tool, using {@link CoapClient} from Californium CoaP library.
 * This class should be
 *
 * This class is used
 *
 * Note: Consider extending CoapClient instead of utilization of it.
 */
public class CoapControlClient {

    private String uri;
    private static final String PROTOCOL = "coap://";
    private static final int COAP_PORT = NetworkConfig.getStandard().getInt(NetworkConfig.Keys.COAP_PORT);

    private CoapClient client = new CoapClient();

    public CoapControlClient(String uri) {
        this.uri = uri;
        client.setURI(uri);
    }

    //TODO: normalize URI, to prevent cases like: localhost:5683//{label}

    /**
     * Get request using {@link CoapClient#get()}. This method provides
     * additional URI management.
     *
     * @param resource
     * @throws ConnectorException if an issue specific to the connector occurred
     * @throws IOException if any other issue (not specific to the connector) occurred
     */
    public void get(String resource) throws ConnectorException, IOException {
        client.setURI(getUri() + resource);
        client.get();
    }

    /**
     *
     * @param resource Post request using {@link CoapClient#post(String payload, int format)}
     * @param payload
     * @throws ConnectorException if an issue specific to the connector occurred
     * @throws IOException if any other issue (not specific to the connector) occurred
     */
    public void post(String resource, String payload) throws ConnectorException, IOException {
        client.setURI(getUri() + resource);
        client.post(payload, MediaTypeRegistry.TEXT_PLAIN);
    }

    /**
     *
     * @param label
     * @return
     * @throws ConnectorException if an issue specific to the connector occurred
     * @throws IOException if any other issue (not specific to the connector) occurred
     */
    public ClientResourceHandler getDevice(String label) throws ConnectorException, IOException {
        Pattern pattern = Pattern.compile(String.format("/%s(/|$)", label));

        Set<String> deviceEndpoints = client.discover()
                .stream()
                .map(WebLink::getURI)
                .filter(pattern.asPredicate())
                .collect(Collectors.toSet());

        return new ClientResourceHandler(this, deviceEndpoints, label);
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
