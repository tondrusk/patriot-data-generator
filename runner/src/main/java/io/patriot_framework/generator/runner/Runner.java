package io.patriot_framework.generator.runner;

import io.patriot_framework.generator.controll.server.CoapControlServer;
import io.patriot_framework.generator.device.Device;
import io.patriot_framework.generator.device.active.Active;
import io.patriot_framework.generator.utils.JSONSerializer;

import java.io.File;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadis</a>
 */
public class Runner {
    public static final String DEVICE_CONFIG_FILE = "PATRIOT_DATA_GENERATOR_DEVICE_FILE";
    public static final String ACTIVE_CONFIG_FILE = "PATRIOT_DATA_GENERATOR_ACTIVE_FILE";

    public static void main(String[] args) {
        if (System.getenv(DEVICE_CONFIG_FILE) != null) {
            String deviceFile = System.getenv(DEVICE_CONFIG_FILE);
            Device device = JSONSerializer.deserializeDevice(new File(deviceFile));
            device.registerToCoapServer();
        }

        if (System.getenv(ACTIVE_CONFIG_FILE) != null) {
            String file = System.getenv(ACTIVE_CONFIG_FILE);
            Active activeDevice = JSONSerializer.deserializeActiveDevice(new File(file));
            activeDevice.getDevice().registerToCoapServer();
            activeDevice.start();
        }
        CoapControlServer.getInstance().start();
    }
}
