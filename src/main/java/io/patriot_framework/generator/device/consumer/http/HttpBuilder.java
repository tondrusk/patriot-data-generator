package io.patriot_framework.generator.device.consumer.http;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HttpBuilder {
    private UUID uuid;
    private String host;
    private int port;

    private final String requestMethod;
    private final String endpoint;
    private final String protocolVersion;
    private final Map<String, String> headers;
    private final Map<String, List<String>> queryParams;

    private final byte[] payload;

    public HttpBuilder(FullHttpRequest request) {
        requestMethod = request.method().toString();
        endpoint = request.uri().split("\\?", 2)[0];
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
        queryParams = queryStringDecoder.parameters();
        protocolVersion = request.protocolVersion().toString();
        headers = RequestUtils.parseHttpHeaders(request.headers());
        payload = RequestUtils.payloadReader(request);
    }

    public HttpBuilder setUUID(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public HttpBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    public HttpBuilder setPort(int port) {
        this.port = port;
        return this;
    }

    public HttpData build() {
        HttpMetaImpl meta = new HttpMetaImpl(uuid, host, port, requestMethod, endpoint, protocolVersion, headers, queryParams);
        return new HttpData(meta, payload);
    }
}
