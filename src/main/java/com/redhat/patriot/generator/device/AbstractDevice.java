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

package com.redhat.patriot.generator.device;

import com.redhat.patriot.generator.dataFeed.DataFeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jsmolar on 7/3/18.
 */
public abstract class AbstractDevice implements Device {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractDevice.class);

    protected String label;

    protected DataFeed dataFeed;

    public AbstractDevice(String label) {
        this.label = label;
    }

    public AbstractDevice(String label, DataFeed dataFeed) {
        this.label = label;
        this.dataFeed = dataFeed;
    }

    @Override
    public double requestData(Object... param) {
        return dataFeed.getNextValue(param);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public DataFeed getDataFeed() {
        return dataFeed;
    }

    public void setDataFeed(DataFeed dataFeed) {
        this.dataFeed = dataFeed;
    }

}
