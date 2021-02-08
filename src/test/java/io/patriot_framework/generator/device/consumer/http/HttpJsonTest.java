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
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpJsonTest extends HttpTestBase{
    HttpData data;
    JSONObject request;

    @BeforeEach
    void runServer() throws ConsumerException, IOException {
        super.runServer(false);

        request = new JSONObject();

        request.put("ID", 1);
        request.put("name", "Johny");

        given().
                contentType(ContentType.JSON).
                header("Content-Type", "application/json").
                body(request.toString()).
        when().
                post("http://localhost:8080").
        then().
                statusCode(200).
                statusLine("HTTP/1.1 200 OK");

        data = (HttpData) storage.get();
    }

    @AfterEach
    void closeServer() {
        super.closeServer();
    }

    @Test
    void getHeaders() {
        Map<String, String> headers = data.getMeta().getHeaders();

        assertEquals("application/json", headers.get("Content-type"));
    }
}
