package io.patriot_framework.generator.device.consumer.mqtt;

import io.patriot_framework.generator.device.consumer.ConsumerMeta;

/**
 * MQTT metadata representation.
 */
public interface MqttMeta extends ConsumerMeta {
    /**
     * @return topic
     */
    String getTopic();

    /**
     * @return ID
     */
    int getId();

    /**
     * @return Quality of Service
     */
    int getQos();

    boolean isDuplicate();

    boolean isRetained();
}
