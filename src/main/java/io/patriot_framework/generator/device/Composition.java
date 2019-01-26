/*
 * Copyright 2019 Patriot project
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

package io.patriot_framework.generator.device;

import io.patriot_framework.generator.dataFeed.DataFeed;

import java.util.List;

/**
 * Interface enables multiple DataFeeds for single device, but preserves single identification parameters.
 * Therefore it needs several different DataFeeds, where values are generated simultaneously.
 *
 * User should use this interface instead of Device.
 *
 * @param <T> type of object with which DataFeeds operates
 */
public interface Composition<T> {

    /**
     * Adds DataFeed to Composition
     *
     * @param dataFeed instance of DataFeed
     */
    void addDataFeed(DataFeed<T> dataFeed);

    /**
     * Removes DataFeed from Composition
     *
     * @param dataFeed instance of DataFeed
     */
    void removeDataFeed(DataFeed<T> dataFeed);

    /**
     * Returns list of all DataFeeds for Composition
     *
     * @return list of DataFeeds
     */
    List<DataFeed<T>> getDataFeed();

}
