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

import io.patriot_framework.generator.device.consumer.ConsumerData;
import io.patriot_framework.generator.device.consumer.Storage;
import io.patriot_framework.generator.device.consumer.exceptions.ConsumerException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServerTest {
    Server server;
    static int port = 8080;
    Storage storage;

    HttpClient httpClient;
    HttpPost httpPost;

    @BeforeEach
    void runServer() throws ConsumerException {
        storage = new Storage();
        server = new Server("localhost", port, storage);
        server.start();
    }

    @AfterEach
    void closeServer() {
        server.stop();
    }

    void createHttpClientWithPayload(String payload, int port) throws UnsupportedEncodingException {
        httpClient = HttpClients.createDefault();

        httpPost = new HttpPost("http://localhost:" + port);
        httpPost.setHeader("Content-type", "text/plain");
        httpPost.setEntity(new StringEntity(payload));
    }

    @Test
    void checkFIFO() throws IOException {
        for (int i = 1; i <= 3; i++) {
            createHttpClientWithPayload("payload-" + i, port);
            httpClient.execute(httpPost);
        }

        assertArrayEquals("payload-1".getBytes(), storage.get().getPayload());
    }

    @Test
    void checkStorage() throws IOException {
        for (int i = 1; i <= 5; i++) {
            createHttpClientWithPayload("payload-" + i, port);
            httpClient.execute(httpPost);
        }

        List<Byte> expected = Stream.of("1", "2", "3", "4", "5")
                .map(s -> s.getBytes()[0])
                .collect(Collectors.toList());

        List<Byte> result = storage.get(5).stream()
                .map(ConsumerData::getPayload)
                .map(payload -> payload[payload.length - 1])
                .collect(Collectors.toList());

        assertEquals(expected, result);
    }

    // TODO HttpDataTest.java

    @Test
    void checkPayload() throws IOException {
        createHttpClientWithPayload("payload", port);
        httpClient.execute(httpPost);

        assertArrayEquals("payload".getBytes(), storage.get().getPayload());
    }

    @Test
    void checkTimestamp() throws IOException {
        createHttpClientWithPayload("payload", port);
        httpClient.execute(httpPost);

        LocalDateTime timestamp = storage.get().getMeta().getTimestamp();

        assertTrue(timestamp.compareTo(LocalDateTime.now().minusSeconds(3)) >= 0);
    }


    @Test
    void checkRequestMethod() throws IOException {
        createHttpClientWithPayload("payload", port);
        httpClient.execute(httpPost);

        assertEquals("POST", ((HttpMeta) storage.get().getMeta()).getRequestMethod());
    }
}
