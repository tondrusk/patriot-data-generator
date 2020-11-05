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

package io.patriot_framework.generator.device.consumer.http;

import io.patriot_framework.generator.device.consumer.exceptions.ConsumerException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HttpDataTest extends HttpTestBase {
    HttpData httpData;

    @BeforeEach
    void runServer() throws ConsumerException, IOException {
        super.runServer(false);

        createHttpClientWithPayload("payload", port);
        httpClient.execute(httpPost);

        httpData = (HttpData) storage.get();
    }

    @AfterEach
    void closeServer() {
        super.closeServer();
    }

    @Test
    void getUUID() {
        assertEquals(server.getUUID(), httpData.getMeta().getUUID());
    }

    @Test
    void getTimestamp() {
        LocalDateTime timestamp = httpData.getMeta().getTimestamp();

        assertTrue(timestamp.compareTo(LocalDateTime.now().minusSeconds(3)) >= 0);
    }

    @Test
    void getPayload() {
        assertArrayEquals("payload".getBytes(), httpData.getPayload());
    }

    @Test
    void getRequestMethod() {
        assertEquals("POST", httpData.getMeta().getRequestMethod());
    }

    @Test
    void getEndpoint() {
        assertEquals("/endpoint", httpData.getMeta().getEndpoint());
    }

    @Test
    void getProtocolVersion() {
        assertEquals("HTTP/1.1", httpData.getMeta().getProtocolVersion());
    }

    @Test
    void getHeaders() {
        Map<String, String> headers = httpData.getMeta().getHeaders();

        assertEquals("text/plain", headers.get("Content-type"));
    }

    @Test
    void getQueryParams() { // TODO ? @mijaros -> List via URL
        Map<String, List<String>> queryParams = httpData.getMeta().getQueryParams();

        assertEquals("value1", queryParams.get("key1").get(0));
        assertEquals("value2", queryParams.get("key1").get(1));
        assertEquals("value3", queryParams.get("key2").get(0));
    }
}