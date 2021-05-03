/*
 * Copyright 2021 Patriot project
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.patriot_framework.generator.serialization;

import io.patriot_framework.generator.device.Device;
import io.patriot_framework.generator.device.impl.basicActuators.BasicActuator;
import io.patriot_framework.generator.device.impl.basicActuators.BinaryActuator;
import io.patriot_framework.generator.device.impl.basicActuators.LinearActuator;
import io.patriot_framework.generator.device.impl.basicActuators.RotaryActuator;
import io.patriot_framework.generator.utils.JSONSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author <a href="mailto:jakub.smadis@gmail.com">Jakub Smadis</a>
 */
public class ActuatorSerializationTest {

    private File file;

    @BeforeEach
    void setup() throws IOException {
        file = File.createTempFile("thermometer", ".json");
        file.deleteOnExit();
    }


    @Test
    void rotaryActuator(){
        Device actuator = new RotaryActuator("rotor", 100.42);

        JSONSerializer.serialize(actuator, file);
        Device another = JSONSerializer.deserializeDevice(file);
        assert (another.equals(actuator));
    }

    @Test
    void linearActuator(){
        Device actuator = new LinearActuator("line", 42);

        JSONSerializer.serialize(actuator, file);
        Device another = JSONSerializer.deserializeDevice(file);
        assert (another.equals(actuator));
    }

    @Test
    void binaryActuator(){
        Device actuator = new BinaryActuator("binary");

        JSONSerializer.serialize(actuator, file);
        Device another = JSONSerializer.deserializeDevice(file);
        assert (another.equals(actuator));
    }

    @Test
    void basicActuator(){
        Device actuator = new BasicActuator("basic");

        JSONSerializer.serialize(actuator, file);
        Device another = JSONSerializer.deserializeDevice(file);
        assert (another.equals(actuator));
    }
}
