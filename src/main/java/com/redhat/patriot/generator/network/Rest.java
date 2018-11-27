/*
 * Copyright 2018 Patriot project
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

package com.redhat.patriot.generator.network;

import com.redhat.patriot.generator.device.Device;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Rest implements NetworkAdapter {

    private String endpoint;

    public Rest(String endpoint) {
        this.endpoint = endpoint;
    }

    public void send(Device device, double value) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        StringEntity se = null;
        try {
            se = new StringEntity(device.getDataWrapper().wrapData(device, value).toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            HttpPost request = new HttpPost(endpoint);
            request.setEntity(se);
            request.addHeader("content-type", "application/json");
            httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
