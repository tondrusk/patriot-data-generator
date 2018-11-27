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

import com.redhat.patriot.generator.device.AbstractDevice;
import com.redhat.patriot.generator.device.Device;
import org.json.JSONObject;

/**
 * Created by jsmolar on 7/24/18.
 */
public class JSONWrapper implements DataWrapper {

    private JSONObject jsonObject;

    public JSONObject wrapData(AbstractDevice device, double data) {
        jsonObject = new JSONObject();
        jsonObject.put("name", device.getLabel())
            .put("data", data);
            //.put("time", data.getCurrentTime());

        System.out.println("data: " + data);

        return jsonObject;
    }

    @Override
    public Object wrapData(Device device, double datae) {
        return null;
    }
}
