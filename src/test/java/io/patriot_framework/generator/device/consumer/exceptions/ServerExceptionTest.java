/*
 * Copyright 2020 Patriot project
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

package io.patriot_framework.generator.device.consumer.exceptions;

import io.patriot_framework.generator.device.consumer.Storage;
import io.patriot_framework.generator.device.consumer.http.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.net.BindException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServerExceptionTest {
    Server server;

    @Test
    void throwsConsumerException() {
        server = new Server("hostlocal", 8080, false, new Storage());

        assertThrows(ConsumerException.class, server::run);
    }

    @Test
    void invalidHostname() {
        server = new Server("hostlocal", 8080, false, new Storage());

        ConsumerException exception = assertThrows(ConsumerException.class, server::run);
        assertEquals("invalid hostname", exception.getMessage());
    }

    @Test
    void occupiedPort() throws ConsumerException {
        server = new Server("localhost", 8080, false, new Storage());
        server.run();

        BindException exception = assertThrows(BindException.class, server::run);
        assertEquals("Address already in use", exception.getMessage());
    }
}
