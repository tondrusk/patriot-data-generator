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
import org.apache.activemq.broker.BrokerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.jms.JMSException;

public class MqttBaseTest {
    Storage storage = new Storage();
    MqttClient client;
    BrokerService broker;

    @BeforeEach
    void createBroker() throws Exception {
        this.broker = new BrokerService();
        broker.addConnector("tcp://localhost:8555");
        broker.start();
    }

    void startMqttClient() {
        storage = new Storage();
        client = new MqttClient("tcp://localhost:8555", "front-door", "patriot", 1, storage);
        client.run();
    }

    @AfterEach
    void close() throws Exception {
        client.close();
        broker.stop();
    }

    @Test
    void runTest() throws JMSException {
        MqttPublisher publisher = new MqttPublisher();
        System.out.println("Preparing to publish");
        publisher.create("Patriot-Publisher", "front-door");
        publisher.publish("Opened");
        publisher.closeConnection();
        startMqttClient();
    }
}
