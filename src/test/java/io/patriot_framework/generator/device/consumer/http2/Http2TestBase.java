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

package io.patriot_framework.generator.device.consumer.http2;

import io.patriot_framework.generator.device.consumer.Storage;
import io.patriot_framework.generator.device.consumer.exceptions.ConsumerException;
import io.patriot_framework.generator.device.consumer.http.Server;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http2.client.HTTP2Client;
import org.eclipse.jetty.http2.client.http.HttpClientTransportOverHTTP2;
import org.junit.jupiter.api.AfterEach;

public abstract class Http2TestBase {

    Server server;
    static int port = 8080;
    Storage storage = new Storage();

    void createHttp2Client() throws Exception {
        HTTP2Client http2Client = new HTTP2Client();
        HttpClient httpClient = new HttpClient(new HttpClientTransportOverHTTP2(http2Client));
        httpClient.start();

        Request request = httpClient.POST("http://localhost:" + port);
        request.header(HttpHeader.CONTENT_TYPE, "text/plain");
        request.content(new StringContentProvider("Payload"));

        System.out.println("Trying to post to http://localhost:" + port);
        request.send();
        System.out.println("Posted to http://localhost:" + port);
        httpClient.stop();
    }

    void runServer(boolean ssl) throws ConsumerException {
        storage = new Storage();
        server = new Server("localhost", port, ssl, storage);
        server.run();
    }

    @AfterEach
    void closeServer() {
        server.close();
    }
}
