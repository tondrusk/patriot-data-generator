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

package com.redhat.patriot.generator.wrappers;

import java.io.IOException;

import com.redhat.patriot.generator.device.AbstractDevice;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

/**
 * Created by jsmolar on 7/24/18.
 */
public class JSONWrapper extends DataWrapper {

    private JSONObject jsonObject;

    @Override
    public JSONObject wrapData(AbstractDevice device, double data) {
        jsonObject = new JSONObject();
        jsonObject.put("name", device.getName())
            .put("data", data);
            //.put("time", data.getTime());

        System.out.println("data: " + data);

        return jsonObject;
    }

    @Override
    public void send() {
        HttpClient httpClient = HttpClientBuilder.create().build();

        try {
            HttpPost request = new HttpPost("http://requestbin.fullcontact.com/s52om8s5");
            request.setEntity(new StringEntity(jsonObject.toString()));
            request.addHeader("content-type", "application/json");
            httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
