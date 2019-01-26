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

package io.patriot_framework.generator.dataFeed;

/**
 * Source of data for Device and TimeSimulation classes. For stochastic data simulation library uses
 * SSJ (Stochastic Simulation in Java) - developed at Université de Montréal
 * https://www.iro.umontreal.ca/~lecuyer/ssj-gh-pages/html/index.html
 *
 * @param <E> Type of generated data
 */
public interface DataFeed<E> {

    /**
     * Returns value from DataFeed. This method is used to compute new data.
     * Flexibility with number of parameters aims to provide freedom in implementation of whole DataFeed.
     *
     * @param params for DataFeed computing
     * @return computed value
     */
    E getNextValue(Object... params);

    /**
     * Returns last generated value from DataFeed.
     * Important for easy comparison between new and previous values.
     *
     * @return last generated value
     */
    E getPreviousValue();

    /**
     * Sets label for DataFeed
     *
     * @param label
     */
    void setLabel(String label);

    /**
     * Returns label for DataFeed
     *
     * @return label
     */
    String getLabel();

}
