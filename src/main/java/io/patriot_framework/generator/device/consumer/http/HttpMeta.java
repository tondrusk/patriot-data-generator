package io.patriot_framework.generator.device.consumer.http;

import io.patriot_framework.generator.device.consumer.ConsumerMeta;

import java.util.List;
import java.util.Map;

/**
 * HTTP metadata representation.
 */
public interface HttpMeta extends ConsumerMeta {
    /**
     * @return hostname
     */
    String getHost();

    /**
     * @return port
     */
    int getPort();

    /**
     * @return request method
     */
    String getRequestMethod();

    /**
     * @return endpoint
     */
    String getEndpoint();

    /**
     * @return protocol version
     */
    String getProtocolVersion();

    /**
     * @return HTTP headers
     */
    Map<String, String> getHeaders();

    /**
     * @return query parameters
     */
    Map<String, List<String>> getQueryParams();
}
