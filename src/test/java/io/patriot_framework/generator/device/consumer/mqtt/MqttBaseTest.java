/*
 * Copyright 2020 Patriot project
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

package io.patriot_framework.generator.device.consumer.mqtt;

import io.patriot_framework.generator.device.consumer.Storage;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MqttBaseTest {
    Storage storage = new Storage();
    MqttClient subscriber;
    MqttBroker broker;
    MqttPublisher publisher;

    void startMqttClient() {
        storage = new Storage();
        subscriber = new MqttClient("tcp://localhost:8883", "front-door", "patriot", 1, storage);
        subscriber.run();
    }

    @BeforeEach
    void createBroker() throws Exception {
        broker = new MqttBroker();
        broker.startMQTTBroker();
    }

    @AfterEach
    void close() {
        subscriber.close();
        broker.stopMQTTBroker();
    }

//    @Test
//    void runTest() throws MqttException {
//        startMqttClient();
//        publisher = new MqttPublisher("tcp://localhost:8883", "tester");
//        publisher.publish("front-door", "opened");
//    }
}
