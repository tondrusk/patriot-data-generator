package com.redhat.patriot.generator.timeSimulation;

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
