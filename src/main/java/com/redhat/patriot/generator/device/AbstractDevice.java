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

package com.redhat.patriot.generator.device;

import com.redhat.patriot.generator.events.DataObserable;
import com.redhat.patriot.generator.network.NetworkAdapter;
import com.redhat.patriot.generator.wrappers.DataWrapper;

import java.util.UUID;

public abstract class AbstractDevice implements Device {

    private UUID uuid = UUID.randomUUID();

    private String label;

    private NetworkAdapter networkAdapter;

    private DataWrapper dataWrapper;

    private DataObserable dataObserable;

    public AbstractDevice(String label) {
        this.label = label;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public DataObserable getDataObserable() {
        return dataObserable;
    }

    @Override
    public void setDataObserable(DataObserable dataObserable) {
        this.dataObserable = dataObserable;
    }

    @Override
    public void setNetworkAdapter(NetworkAdapter networkAdapter) {
        this.networkAdapter = networkAdapter;
    }

    @Override
    public NetworkAdapter getNetworkAdapter() {
        return networkAdapter;
    }

    @Override
    public DataWrapper getDataWrapper() {
        return dataWrapper;
    }

    @Override
    public void setDataWrapper(DataWrapper dataWrapper) {
        this.dataWrapper = dataWrapper;
    }
}
