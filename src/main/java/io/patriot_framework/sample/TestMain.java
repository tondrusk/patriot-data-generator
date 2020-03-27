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

package io.patriot_framework.sample;

import io.patriot_framework.generator.device.impl.basicActuators.LinearActuator;
import io.patriot_framework.generator.device.passive.actuators.Actuator;
import io.patriot_framework.generator.device.passive.actuators.StateMachine;
import io.patriot_framework.generator.device.passive.actuators.Transition;

public class TestMain {

    public static void main(String[] args) {
//        DataFeed df = new NormalDistributionDataFeed(18, 2);
//        NetworkAdapter na = new Rest("http://requestbin.fullcontact.com/vlr0agvl", new JSONWrapper());
//        SimpleSensor temperature = new Thermometer("thermometer", df);
//        temperature.setNetworkAdapter(na);
////        temperature.registerToCoapServer();
////


//        DataFeed df = new NormalDistributionDataFeed(18, 2);
//        SimpleSensor temperature = new Thermometer("thermometer", df);
//        temperature.registerToCoapServer();
//
//        DataFeed tf = new ConstantDataFeed(10000);
//        ActiveDevice simulation = new ActiveDeviceImpl(tf, temperature);
//        simulation.start();
//
//        CoapControlClient c = new CoapControlClient("coap://localhost:5683");
//
//        try {
//            c.getDevice("thermometer").toggleDevice();
//        } catch (ConnectorException | IOException e) {
//            e.printStackTrace();
//        }

        Actuator actuator = new LinearActuator("trt", 10000);
        actuator.registerToCoapServer();

        StateMachine st = new StateMachine.Builder()
                .from("Zasunute")
                    .to("Pohyb", Transition.DOWN)
                .from("Vysunute")
                .   to("Pohyb", Transition.UP)
                .from("Pohyb", 10000)
                    .to("Vysunute", Transition.DOWN)
                    .to("Zasunute", Transition.UP)
//                    .to("Halt")
                .build();

        actuator.setStateMachine(st);

        st.transition(Transition.DOWN);
        st.transition(Transition.DOWN);

        /**
        kavovar - loop


         Vysunute  ----> Zas(60) ---> poloha
         Zasuvaju sa
         Zsunute
         Vysuvaju sa
         Rotacia - x - uhol
         */



//        {
//            "dataFeed": {
//            "className": "io.patriot_framework.generator.dataFeed.ConstantDataFeed",
//                    "label: "testUpdate"
//            "timer": "2031232100"
//        }
//        }

    }

}
