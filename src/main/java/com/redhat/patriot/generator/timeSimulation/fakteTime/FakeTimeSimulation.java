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

package com.redhat.patriot.generator.timeSimulation.fakteTime;

import com.redhat.patriot.generator.device.Device;
import umontreal.ssj.randvar.ErlangConvolutionGen;
import umontreal.ssj.randvar.ErlangGen;
import umontreal.ssj.rng.MRG32k3a;
import umontreal.ssj.simevents.Sim;

/**
 * Created by jsmolar on 7/5/18.
 */
public class FakeTimeSimulation {

    private static final double minute = 1.0 / 60.0;

    private Device device;

    public FakeTimeSimulation(Device device) {
        this.device = device;
    }

    public void simDataInTime(int time) {
        ErlangGen timeGeneration = new ErlangConvolutionGen(new MRG32k3a(), 2, 1.0 / minute);

        DataEvent dataEvent = new DataEvent(device, true);

        Sim.init();
        new EndOfSimulation().schedule(time);
        dataEvent.schedule(1);
        Sim.start();

        System.out.println(dataEvent.getSimulatedData());
    }

    public void simDataInRuns(int runs) {
        ErlangGen timeGeneration = new ErlangConvolutionGen(new MRG32k3a(), 2, 1.0 / minute);

        DataEvent dataEvent = new DataEvent(device, true);

        for(int i = 0; i < runs; i++) {
            Sim.init();
            dataEvent.schedule(timeGeneration.nextDouble());
            Sim.start();
        }
    }

}
