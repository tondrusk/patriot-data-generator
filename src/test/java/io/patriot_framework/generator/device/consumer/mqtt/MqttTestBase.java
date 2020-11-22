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
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class MqttTestBase {
    String brokerURI = "tcp://localhost:8883";
    MqttBroker broker;
    Storage storage = new Storage();
    MqttConsumer subscriber;
    MqttClient publisher;

    void startSubscriber(String topic) {
        storage = new Storage();
        subscriber = new MqttConsumer(brokerURI, topic, "patriot-subscriber", 2, storage);
        subscriber.run();
    }

    void startSubscriber(String broker, String topic) {
        storage = new Storage();
        subscriber = new MqttConsumer(broker, topic, "patriot-subscriber", 2, storage);
        subscriber.run();
    }

    void summonPublisher(String topic, String message) throws MqttException {
        publisher = new MqttClient(brokerURI, "patriot-publisher");
        publisher.connect();
        publisher.publish(topic, new MqttMessage(message.getBytes()));
        publisher.disconnect();
    }

    @BeforeEach
    void createBroker() throws Exception {
        broker = new MqttBroker();
        broker.startMqttBroker();
    }

    @AfterEach
    void close() {
        broker.stopMqttBroker();
    }

    @AfterEach
    void closeSubscriber() {
        subscriber.close();
    }
}
