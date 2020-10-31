package io.patriot_framework.generator.device.consumer;

import io.patriot_framework.generator.device.Device;
import io.patriot_framework.generator.device.consumer.exceptions.ConsumerException;

/**
 * Interface for consumer management.
 */
public interface Consumer extends Device {
    /**
     * Run the consumer.
     *
     * @throws ConsumerException exception
     */
    void run() throws ConsumerException;

    /**
     * Close the consumer.
     */
    void close();
}
