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

import com.redhat.patriot.generator.data.Data;
import com.redhat.patriot.generator.data.DataComposition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeviceComposition extends AbstractDevice {

    private List<Device> devices = new ArrayList<>();

    public DeviceComposition(String label) {
        super(label);
    }

    public DeviceComposition(String label, Device... device) {
        super(label);
        devices.addAll(Arrays.asList(device));
    }

    @Override
    public Data requestData(Object... param) {
        DataComposition<double[]> a = new DataComposition<>();
        a.addData((double) 2);

        for(Device d : devices) {
            a[0] = 'a';
        }
    }

    @Override
    public String getUnit() {
        return null;
    }

    @Override
    public void setUnit(String unit) {

    }

}
