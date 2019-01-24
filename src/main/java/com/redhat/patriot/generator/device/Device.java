/*
 * Copyright 2018 Patriot project
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

package com.redhat.patriot.generator.device;

import com.redhat.patriot.generator.events.DataObservable;
import com.redhat.patriot.generator.network.NetworkAdapter;
import com.redhat.patriot.generator.wrappers.DataWrapper;

import java.util.List;

/**
 * Base interface describing concept for device. Provides methods for manipulation and configuration.
 *
 * User should not use this interface for creating device objects. Mainly serves for internal object interaction,
 * but lacks DataFeed manipulation.
 *
 * @param <E> the type of generated data
 */
public interface Device<E> extends Unit {

    /**
     * Pulls data from data feed. This method serves as handler for data from DataFeed.
     * Forwards parameters for getNextValue method (see DataFeed.getNextValue).
     *
     * @param params for DataFeed computing
     * @return a list of generated values
     */
    List<E> requestData(Object... params);

    /**
     * Sets Network Adapter for device which enables sending data wi
     *
     * @param networkAdapter instance of NetworkAdapter
     */
    void setNetworkAdapter(NetworkAdapter networkAdapter);

    /**
     * Returns NetworkAdapter assigned to Device
     *
     * @return instance of Network Adapter
     */
    NetworkAdapter getNetworkAdapter();

    /**
     * Returns DataWrapper that is used by Device
     *
     * @return instance of DataWrapper
     */
    DataWrapper getDataWrapper();

    /**
     * Sets DataWrapper for Device, which allows to transform generated data and important
     * information about its origin to common data structures e.g. JSON, XML, for better manipulation.
     *
     * @param dataWrapper instance of DataWrapper
     */
    void setDataWrapper(DataWrapper dataWrapper);

    /**
     * Returns DataObservable for Device
     *
     * @return instance of DataObservable
     */
    DataObservable getDataObservable();

    /**
     * Sets DataObservable for Device.
     *
     * @param dataObservable instance of DataObservable
     */
    void setDataObservable(DataObservable dataObservable);

}
