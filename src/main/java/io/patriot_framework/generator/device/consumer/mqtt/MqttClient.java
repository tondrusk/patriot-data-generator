package io.patriot_framework.generator.device.consumer.mqtt;

import io.patriot_framework.generator.Data;
import io.patriot_framework.generator.device.AbstractDevice;
import io.patriot_framework.generator.device.consumer.Consumer;
import io.patriot_framework.generator.device.consumer.Storage;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class MqttClient extends AbstractDevice implements Consumer, AutoCloseable {
    private MqttAsyncClient mqttClient;
    private String broker;
    private String clientId;
    private String topic;
    private int qos;
    private Storage storage;

    public static final Logger LOGGER = LoggerFactory.getLogger(MqttClient.class);

    public MqttClient(String broker, String topic, String clientId, int qos, Storage storage) {
        this.broker = broker;
        this.clientId = clientId;
        this.qos = qos;
        this.storage = storage;
        this.topic = topic;
    }

    public void run() {
        try {
            LOGGER.info("Trying to connect MQTT client to " + broker);
            mqttClient = new MqttAsyncClient(broker, clientId);

            IMqttToken token = mqttClient.connect();
            token.waitForCompletion();

            LOGGER.info("MQTT client connected to " + broker);

            mqttClient.setCallback(new Callback(getUUID(), storage));
            mqttClient.subscribe(topic, qos);

            LOGGER.info("MQTT client subscribed to " + topic + " with QoS " + qos);
        } catch (MqttException e) {
            LOGGER.error("Reason code: " + e.getReasonCode());
            LOGGER.error("Message: " + e.getMessage());
            LOGGER.error("Localized message: " + e.getLocalizedMessage());
            LOGGER.error("Cause: " + e.getCause());
            LOGGER.error("Exception: " + e);
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if (mqttClient.isConnected()) {
            try {
                mqttClient.disconnect();
                LOGGER.info("MQTT client disconnected from " + broker);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Data> requestData(Object... params) {
        throw new NotImplementedException();
    }
}
