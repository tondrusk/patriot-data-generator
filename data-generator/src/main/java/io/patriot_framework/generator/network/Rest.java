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

package io.patriot_framework.generator.network;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.patriot_framework.generator.Data;
import io.patriot_framework.generator.network.wrappers.DataWrapper;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

public class Rest implements NetworkAdapter {

    private String endpoint;

    private DataWrapper dataWrapper;

    @JsonCreator
    public Rest(@JsonProperty("endpoint") String endpoint, @JsonProperty("wrapper") DataWrapper dataWrapper) {
        this.endpoint = endpoint;
        this.dataWrapper = dataWrapper;
    }

    @Override
    public void send(List<Data> data) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        dataWrapper.wrapData(data);

        try {
            HttpPost request = new HttpPost(endpoint);
            StringEntity se = new StringEntity(dataWrapper.wrapData(data));
            request.setEntity(se);
            request.addHeader("content-type", "application/json");
            httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDataWrapper(DataWrapper dataWrapper) {
        this.dataWrapper = dataWrapper;
    }

    @Override
    public DataWrapper getDataWrapper() {
        return dataWrapper;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

}

