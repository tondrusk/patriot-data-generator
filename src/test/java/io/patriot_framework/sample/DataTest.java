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

package io.patriot_framework.sample;

import io.patriot_framework.generator.Data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataTest {

    @Test
    public void dataShouldStoreTypeValue() {
        double testValue = 1234.4321;
        Class<?> testClass = Double.class;
        Data testData = new Data(testClass, testValue);

        assertEquals(testValue, testData.get(testClass));
    }

    @Test
    public void shuldHandleErrorWithWringTypes() {
        Data testData = new Data(Double.class, 1234);
        testData.get(String.class);
        assertThrows(ClassCastException.class, () -> testData.get(String.class));
    }

}
