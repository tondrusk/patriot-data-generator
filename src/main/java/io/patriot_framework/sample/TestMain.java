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

import io.patriot_framework.generator.basicActuators.LinearActuator;
import io.patriot_framework.generator.device.passive.Actuator;

public class TestMain {

    public static void main(String[] args) {
//        DataFeed df = new NormalDistributionDataFeed(18, 2);
////        NetworkAdapter na = new Rest("http://requestbin.fullcontact.com/wv2m0ywv");
//        DataProducer temperature = new Thermometer("thermometer", df);
////        temperature.setNetworkAdapter(na);
//        temperature.startCoapController();
//
//        DataFeed tf = new ConstantDataFeed(10000);
//        ActiveDevice simulation = new ActiveDeviceImpl(tf, temperature);
//        simulation.startSimulation();

        Actuator actuator = new LinearActuator("aa", 15000);
        actuator.startCoapController();


    }

}
