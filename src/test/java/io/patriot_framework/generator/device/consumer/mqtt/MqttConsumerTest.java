package io.patriot_framework.generator.device.consumer.mqtt;

import io.patriot_framework.generator.device.consumer.exceptions.ConsumerException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MqttConsumerTest extends MqttTestBase {

    @Test
    void simpleMessage() throws MqttException, ConsumerException {
        startSubscriber("front-door");
        summonPublisher("front-door", "patriot");

        assertArrayEquals("patriot".getBytes(), storage.get().getPayload());
    }

    @AfterEach
    void closeSubscriber() {
        subscriber.stop();
    }
}