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

import com.redhat.patriot.generator.basicDevices.Hygrometer;
import com.redhat.patriot.generator.dataFeed.DataFeed;
import com.redhat.patriot.generator.dataFeed.NormalDistributionDataFeed;
import com.redhat.patriot.generator.device.Device;
import org.junit.jupiter.api.BeforeEach;

class DeviceTest {

    private Device newDevice;

    @BeforeEach
    void setUp() {
        DataFeed dataFeed = new NormalDistributionDataFeed(0, 1);
        newDevice = new Hygrometer("TestHygrometer", dataFeed);
    }

//    @Test
//    void getValue() {
//        double data = newDevice.requestData();
//        assertTrue(data <= 2 );
//        assertTrue(data >= -2 );
//    }
//
//    @Test
//    void getValueWithoutDataFeed() {
//        newDevice.setDataFeed(null);
//
//        assertThrows(IllegalArgumentException.class, () -> newDevice.requestData());
//    }

//    @Test
//    void dataQueue() throws InterruptedException {
//        newDevice.setQueuingEnabled(true);
//        DataQueue queue = DataQueue.getInstance();
//
//        newDevice.requestData();
//        assertNotNull(queue.take());
//    }

}
