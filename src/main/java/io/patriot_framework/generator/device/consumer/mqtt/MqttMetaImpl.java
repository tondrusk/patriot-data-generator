package io.patriot_framework.generator.device.consumer.mqtt;

import java.time.LocalDateTime;
import java.util.UUID;

public final class MqttMetaImpl implements MqttMeta {
    private final UUID uuid;
    private final String topic;
    private final int id;
    private final int qos;
    private final boolean isDuplicate;
    private final boolean isRetained;

    private final LocalDateTime timestamp = LocalDateTime.now();

    public MqttMetaImpl(UUID uuid, String topic, int id, int qos, boolean isDuplicate, boolean isRetained) {
        this.uuid = uuid;
        this.topic = topic;
        this.id = id;
        this.qos = qos;
        this.isDuplicate = isDuplicate;
        this.isRetained = isRetained;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
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
