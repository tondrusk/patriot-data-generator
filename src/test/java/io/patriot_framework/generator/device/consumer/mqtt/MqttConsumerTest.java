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

import io.patriot_framework.generator.device.consumer.exceptions.ConsumerException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MqttConsumerTest extends MqttTestBase {

    @Test
    void simpleMessage() throws MqttException, ConsumerException {
        startSubscriber("front-door");
        summonPublisher();
        publish("front-door", "patriot");

        assertArrayEquals("patriot".getBytes(), storage.get().getPayload());
    }
}