package io.patriot_framework.generator.device.consumer.http;

import javax.net.ssl.SSLContext;

public interface SSLInit {

    /**
     * Create SSL context for SSL engine in the {@code ServerInitializer} class.
     *
     * @return SSL context
     */
    SSLContext createSslContext();
}
