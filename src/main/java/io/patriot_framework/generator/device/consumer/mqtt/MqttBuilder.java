package io.patriot_framework.generator.device.consumer.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.UUID;

public class MqttBuilder {
    private UUID uuid;
    private final String topic;
    private final int id;
    private int qos;
    private boolean isDuplicate;
    private boolean isRetained;

    private byte[] payload;

    public MqttBuilder(String topic, MqttMessage message) {
        this.topic = topic;
        this.id = message.getId();
        this.qos = message.getQos();
        this.isDuplicate = message.isDuplicate();
        this.isRetained = message.isRetained();
        this.payload = message.getPayload();
    }

    public MqttBuilder setUUID(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public MqttData build() {
        MqttMetaImpl meta = new MqttMetaImpl(uuid, topic, id, qos, isDuplicate, isRetained);
        return new MqttData(meta, payload);
    }
}
