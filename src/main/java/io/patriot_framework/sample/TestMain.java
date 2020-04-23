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
import io.patriot_framework.generator.device.passive.actuators.stateMachine.Condition;
import io.patriot_framework.generator.device.passive.actuators.stateMachine.StateMachine;

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

        Condition con = data -> {
            if (data > 30) return "Fill sta";
            return null;
        };

//        StateMachine st = new StateMachine.Builder()
//
//
//
//                .from("Zasunute")
//                    .to("Pohyb D", Transition.DOWN)
//                .from("Vysunute")
//                .   to("Pohyb H", Transition.UP)
//                .from("Pohyb D", 5000)
//                    .to("Vysunute", Transition.DOWN)
//                .from("Pohyb H", 5000)
//                    .to("Zasunute", Transition.UP)
//                .condition(con,10)
////                    .to("Halt")
//                //dat toto ako priklad do prace
//                .from("A")
//                    .to("C", "chuj", "B", 2000)
//                .build();
//
//        actuator.setStateMachine(st);


//        StateMachine machine1 = new StateMachine.Builder()
//                .from("Off")
//                    .to("ON", "start","Starting",  2000)  //jenom string, zmacknuti tlacitka
//                .from("ON")
////                    .to("Make coffee", "make", "Waiting for commands", 2000)
////                .from("Make coffee", 1000)
////                    .to("Checking water", "ajaj")
////                .from("Checking water", 2000)
//                .to("Fill water")
//                    .condition(data -> {
//                        if (true) return "Fill water";
//
//                        return "Enough water";
//                    }, 10)
//                .build();
//        actuator.setStateMachine(machine1);




        StateMachine machine1 = new StateMachine.Builder()
//                .from("Off")
//                .to("ON", "start","Starting",  2000)  //jenom string, zmacknuti tlacitka
//                .from("ON")
//                .to("Make coffee", "make", "Waiting for commands", 2000)
//
//
//
//                .from("Make coffee", 1000)
//                .to("Checking water", "ajaj")

                .from("Checking water", 1000)
                .to("Fill water")
                .to("Enough water")
                .condition(data -> {
                    if (true) return "Enough water";

                    return "Fill water";
                }, 10)

                .from("Fill water",10000)
//                .to("Enough water")
//                .to("Fill water")
//                .condition(checkWatt, defaultWater)
//                .from("Enough water", 1000)
//                .to("Checking coffee", "")
//                .from("Checking coffee", 1000)
//                .condition(checkCoff, defaultCoffee)
//                .from("Fill coffee", 10000)
//                .condition(checkCoff, defaultCoffee)
//                .from("Enough coffee", 1000)
//                .to("Coffee finished", buttonMakeCoff, "Making your coffee", 4000)

                .build();

        actuator.setStateMachine(machine1);




//        st.transition(Transition.DOWN);
//        st.transition(Transition.DOWN);


        //vysonutom
//        st.transition(Transition.DOWN, 60);
//        st.transition(Transition.UP);

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
