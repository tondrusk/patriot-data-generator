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

package com.redhat.patriot.generator.events;

import com.redhat.patriot.generator.device.Device;

import java.util.ArrayList;
import java.util.List;

public class DataListener implements Runnable {

    private DataQueue dataQueue = DataQueue.getInstance();

    private List<Device> subscriptions = new ArrayList<>();

    private boolean active = true;

    @Override
    public void run() {
        while(active) {
            try {
                dataQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Device> getSubscriptions() {
        return subscriptions;
    }

    public void addSubscription(Device device) {
        device.setQueuingEnabled(true);
        subscriptions.add(device);
    }

    public void removeSubscription(Device device) {
        device.setQueuingEnabled(false);
        subscriptions.remove(device);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
