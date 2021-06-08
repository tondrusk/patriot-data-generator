package io.patriot_framework.generator.device.consumer.http;

import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.Map;

public class RequestUtils {
    public static byte[] payloadReader(HttpContent content) {
        return content.content().toString(CharsetUtil.UTF_8).getBytes();
    }

    public static Map<String, String> parseHttpHeaders(HttpHeaders headers) {
        Map<String, String> resultHeaders = new HashMap<>();
        for (Map.Entry<String, String> header : headers) {
            resultHeaders.put(header.getKey(), header.getValue());
        }
        return resultHeaders;
    }
}
