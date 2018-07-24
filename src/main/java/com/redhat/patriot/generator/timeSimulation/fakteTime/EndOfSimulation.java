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

import umontreal.ssj.simevents.Event;
import umontreal.ssj.simevents.Sim;

/**
 * Created by jsmolar on 7/5/18.
 */
public class EndOfSimulation extends Event {

    public void actions() {
        Sim.stop();
    }

}
