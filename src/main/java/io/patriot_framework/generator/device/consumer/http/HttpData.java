package io.patriot_framework.generator.device.consumer.http;

import io.patriot_framework.generator.device.consumer.ConsumerData;

public final class HttpData implements ConsumerData {
    private HttpMeta meta;
    private byte[] payload;

    public HttpData(HttpMeta meta, byte[] payload) {
        this.meta = meta;
        this.payload = payload;
    }

    @Override
    public HttpMeta getMeta() {
        return meta;
    }

    @Override
    public byte[] getPayload() {
        return payload;
    }
}
