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
import com.redhat.patriot.generator.device.timeSimulation.TimeSimulation;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractComposition<E> extends AbstractDevice implements Composition<E> {

    private TimeSimulation ts;

    private List<DataFeed<E>> dataFeed;

    public AbstractComposition(String label) {
        super(label);
    }

    @Override
    public List<E> requestData(Object... param) {
        List<E> result = new ArrayList<>();

        for(DataFeed<E> df : dataFeed) {
            result.add(df.getNextValue());
        }

        return result;
    }

    private void sendData() {

    }

    @Override
    public void addDataFeed(DataFeed<E> dataFeed) {
        this.dataFeed.add(dataFeed);
    }

    @Override
    public void removeDataFeed(DataFeed<E> dataFeed) {
        this.dataFeed.remove(dataFeed);
    }

    @Override
    public List<DataFeed<E>> getDataFeed() {
        return dataFeed;
    }
}
