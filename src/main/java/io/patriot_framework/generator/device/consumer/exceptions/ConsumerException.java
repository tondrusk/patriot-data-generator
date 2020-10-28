package io.patriot_framework.generator.device.consumer.exceptions;

/**
 * Custom consumer exception.
 */
public class ConsumerException extends Exception {
    public ConsumerException() {
        super();
    }

    public ConsumerException(String message) {
        super(message);
    }

    public ConsumerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConsumerException(Throwable cause) {
        super(cause);
    }
}
