package io.patriot_framework.generator.device.consumer;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Generic representation of metadata.
 */
public interface ConsumerMeta {
    /**
     * @return UUID
     */
    UUID getUUID();

    /**
     * @return time when the data were received and processed
     */
    LocalDateTime getTimestamp();
}
