package io.patriot_framework.generator.device.consumer.mqtt;

import io.patriot_framework.generator.device.consumer.ConsumerData;

public final class MqttData implements ConsumerData {
    private MqttMeta meta;
    private byte[] payload;

    public MqttData(MqttMeta meta, byte[] payload) {
        this.meta = meta;
        this.payload = payload;
    }

    @Override
    public MqttMeta getMeta() {
        return meta;
    }

    @Override
    public byte[] getPayload() {
        return payload;
    }
}
