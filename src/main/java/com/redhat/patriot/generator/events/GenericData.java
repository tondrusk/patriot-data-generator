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

package com.redhat.patriot.generator.events;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GenericData {

    private UUID uuid;

    private String device;

    private Set<Double> data = new HashSet<>();

    public GenericData() {
    }

    public GenericData(UUID uuid, String device) {
        this.uuid = uuid;
        this.device = device;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Set<Double> getData() {
        return data;
    }

    public void addData(double data) {
        this.data.add(data);
    }
}
