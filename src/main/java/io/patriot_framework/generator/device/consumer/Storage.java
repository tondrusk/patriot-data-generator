package io.patriot_framework.generator.device.consumer;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Storage which stores data in ConcurrentLinkedQueue for thread-safety.
 */
public class Storage {
    private final ConcurrentLinkedQueue<ConsumerData> dataList = new ConcurrentLinkedQueue<>();

    /**
     * Append input data to the ConcurrentLinkedQueue.
     *
     * @param message input
     */
    public void write(ConsumerData message) {
        if (message == null) {
            return;
        }
        dataList.offer(message);
    }

    /**
     * @return first element of the ConcurrentLinkedQueue
     */
    public ConsumerData get() {
        return dataList.poll();
    }

    /**
     * @param n number of elements to be returned
     * @return first n elements of the ConcurrentLinkedQueue
     */
    public ArrayList<ConsumerData> get(int n) {
        ArrayList<ConsumerData> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ConsumerData element = get();
            if (element == null) {
                break;
            }
            res.add(element);
        }
        return res;
    }

    public int size() {
        return dataList.size();
    }
}
