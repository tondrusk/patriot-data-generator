package io.patriot_framework.generator.device.consumer.mqtt;

import io.patriot_framework.generator.device.consumer.AbstractConsumerMeta;

import java.util.UUID;

public final class MqttMetaImpl extends AbstractConsumerMeta implements MqttMeta {
    private final String topic;
    private final int id;
    private final int qos;
    private final boolean isDuplicate;
    private final boolean isRetained;

    public MqttMetaImpl(UUID uuid, String topic, int id, int qos, boolean isDuplicate, boolean isRetained) {
        super(uuid);
        this.topic = topic;
        this.id = id;
        this.qos = qos;
        this.isDuplicate = isDuplicate;
        this.isRetained = isRetained;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getQos() {
        return qos;
    }

    @Override
    public boolean isDuplicate() {
        return isDuplicate;
    }

    @Override
    public boolean isRetained() {
        return isRetained;
    }
}
