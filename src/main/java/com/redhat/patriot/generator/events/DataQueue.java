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

public class DataQueue implements Runnable {

    private static DataQueue singleInstance = null;

//    private final BlockingQueue<Data> queue = new LinkedBlockingQueue<>();

//    private HashMap<Device, String> sadasd = new HashMap<>();

    private List<Device> devices = new ArrayList<>();

    private List<Device> subscriptions = new ArrayList<>();

    private DataQueue() {
    }

    public static DataQueue getInstance() {
        if (singleInstance == null)
            singleInstance = new DataQueue();

        return singleInstance;
    }

    public void registerDevice(Device device) {
        devices.add(device);
    }

    public void remvoeDevice(Device device) {
        devices.remove(device);
    }

    public void subscribe(Device device) {
        subscriptions.add(device);
    }

    public void removeSubscription(Device device) {
        subscriptions.remove(device);
    }

    public void subscribeAll() {
        devices = subscriptions;
    }

    public void aaaa() {
//        for(Device device : subscriptions) {
//          device.
//        }
    }

    @Override
    public void run() {

    }


//    public BlockingQueue<Data> get() {
//        return queue;
//    }
//
//
//
//    // Inserts the specified element into this queue if it is possible to do so
//    // immediately without violating capacity restrictions
//    public void add(Data value) {
//        synchronized (queue) {
//            queue.add(value);
//        }
//    }
//
//    // Removes a single instance of the specified element from this collection
//    public void remove(Data value) {
//        synchronized (queue) {
//            queue.remove(value);
//        }
//    }
//
//    // Retrieves and removes the head of this queue, or returns null if this
//    // queue is empty.
//    public Data take() throws InterruptedException {
//        Data data = queue.take();
//        return data;
//    }
//
//    // Returns true if this collection contains no elements
//    public boolean isEmpty() {
//        return queue.isEmpty();
//    }
//
//    // Returns the number of elements in this collection. If this collection
//    // contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE
//    public int getTotalSize() {
//        return queue.size();
//    }

}
