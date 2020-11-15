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

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttPublisher {
    private MqttAsyncClient mqttClient;
    private String broker;
    private String clientId;
    public static final Logger LOGGER = LoggerFactory.getLogger(MqttPublisher.class);

    public MqttPublisher(String broker, String clientId) {
        this.broker = broker;
        this.clientId = clientId;
    }

    public void publish(String topic, String message) throws MqttException {
        System.out.println(broker + clientId);
        mqttClient = new MqttAsyncClient(broker, clientId);

        LOGGER.info("Mqtt publisher connecting");
        IMqttToken token = mqttClient.connect();
        token.waitForCompletion();

        LOGGER.info("Publishing");
        mqttClient.publish(topic, new MqttMessage(message.getBytes()));
        if(mqttClient.isConnected()) {
            mqttClient.disconnect();
        }
        LOGGER.info("End of publishing");
    }
}
