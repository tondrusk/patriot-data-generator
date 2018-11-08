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

import com.redhat.patriot.generator.device.Data;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DataQueue {

    private static DataQueue single_instance = null;

    private final BlockingQueue<Data> queue = new LinkedBlockingQueue<>();

    private DataQueue() {
    }

    public static DataQueue getInstance() {
        if (single_instance == null)
            single_instance = new DataQueue();

        return single_instance;
    }

    public BlockingQueue<Data> get() {
        return queue;
    }

    // Inserts the specified element into this queue if it is possible to do so
    // immediately without violating capacity restrictions
    public void add(Data value) {
        synchronized (queue) {
            queue.add(value);
        }
    }

    // Removes a single instance of the specified element from this collection
    public void remove(Data value) {
        synchronized (queue) {
            queue.remove(value);
        }
    }

    // Retrieves and removes the head of this queue, or returns null if this
    // queue is empty.
    public Data take() throws InterruptedException {
        Data data = queue.take();
        return data;
    }

    // Returns true if this collection contains no elements
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // Returns the number of elements in this collection. If this collection
    // contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE
    public int getTotalSize() {
        return queue.size();
    }

}
