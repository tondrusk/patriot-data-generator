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

import io.patriot_framework.generator.device.consumer.ConsumerMeta;

import java.util.List;
import java.util.Map;

/**
 * HTTP metadata representation.
 */
public interface HttpMeta extends ConsumerMeta {

    /**
     * @return hostname
     */
    String getHost();

    /**
     * @return port
     */
    int getPort();

    /**
     * @return request method
     */
    String getRequestMethod();

    /**
     * @return endpoint
     */
    String getEndpoint();

    /**
     * @return protocol version
     */
    String getProtocolVersion();

    /**
     * @return HTTP headers
     */
    Map<String, String> getHeaders();

    /**
     * @return query parameters
     */
    Map<String, List<String>> getQueryParams();
}
