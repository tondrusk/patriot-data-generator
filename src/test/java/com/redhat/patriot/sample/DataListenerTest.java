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

package com.redhat.patriot.sample;

import com.redhat.patriot.generator.dataFeed.ConstantDataFeed;
import com.redhat.patriot.generator.dataFeed.DataFeed;
import com.redhat.patriot.generator.basicDevices.Default;
import com.redhat.patriot.generator.device.Device;
import com.redhat.patriot.generator.events.DataObserable;
import com.redhat.patriot.generator.events.GenericData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Observable;
import java.util.Observer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DataListenerTest {

    private Device device;

    private DataObserable dataListener;

    @BeforeEach
    void setUp() {
        DataFeed df = new ConstantDataFeed(2);
        device = new Default("testDevice", df);

        dataListener = new DataObserable();
    }

    @Test
    void listenerSendsData() {
        GenericData a;

        Observer mockObserver = new Observer() {
            public GenericData catchedData;

            @Override
            public void update(Observable observable, Object o) {
                a = (GenericData) o;
                assertNotNull(catchedData);
            }
        };

        dataListener.addDevice(device);
        dataListener.addObserver(mockObserver);
        device.requestData();

//        assertTrue(((Observer) mockObserver).catchedData != null);
    }

}
