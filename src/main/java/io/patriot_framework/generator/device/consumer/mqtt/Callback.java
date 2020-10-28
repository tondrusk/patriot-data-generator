package io.patriot_framework.generator.device.consumer.mqtt;

import io.patriot_framework.generator.device.consumer.Storage;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.UUID;

/**
 * Callback is called when something happens on topic to which mqttClient is connected
 */
public class Callback implements MqttCallback {
    private UUID uuid;
    Storage storage;

    public Callback(UUID uuid, Storage storage) {
        this.uuid = uuid;
        this.storage = storage;
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) {
        MqttData data = new MqttBuilder(topic, mqttMessage)
                .setUUID(uuid)
                .build();

        storage.write(data);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
