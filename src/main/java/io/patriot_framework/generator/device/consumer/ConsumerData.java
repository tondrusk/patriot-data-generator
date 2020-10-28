package io.patriot_framework.generator.device.consumer;

/**
 * Generic representation of data.
 */
public interface ConsumerData {
    /**
     * @return metadata
     */
    ConsumerMeta getMeta();

    /**
     * @return payload
     */
    byte[] getPayload();
}
