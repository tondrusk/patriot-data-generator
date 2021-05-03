/*
 * Copyright 2021 Patriot project
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

package io.patriot_framework.generator.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.patriot_framework.generator.device.Device;
import io.patriot_framework.generator.device.active.Active;
import io.patriot_framework.generator.device.active.ActiveDevice;

import java.io.File;
import java.io.IOException;

/**
 * Wrapper class for Device serialization to JSON
 *
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadis</a>
 */
public class JSONSerializer {

    public static void serialize(Device device, File file) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file, device);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public static String serialize(Device device) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(device);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e);
        }
    }

    public static void serialize(Active device, File file) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file, device);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public static String serialize(Active device) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(device);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e);
        }
    }

    public static Device deserializeDevice(File file) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(file, Device.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public static Device deserializeDevice(String content) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(content, Device.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public static ActiveDevice deserializeActiveDevice(File file) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(file, ActiveDevice.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public static ActiveDevice deserializeActiveDevice(String content) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(content, ActiveDevice.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}
