package com.jiananwang.iot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * This service receives incoming bytes, puts into queue and dequeue at the proper time
 *
 */
@Service
public class ReceiverService {
    Logger logger = LoggerFactory.getLogger(ReceiverService.class);

    /**
     * Thread safe blocking queue for read and write. Note it receives byte[] instead of byte.
     */
    private BlockingQueue<byte[]> queue = new LinkedBlockingDeque<>();

    /**
     * Keep tracking size of elements in queue
     */
    private long size = 0;


    /**
     * Add byte[] into queue, update size accordingly.
     * @param bytes
     */
    public void put(byte[] bytes) {
        if (bytes == null)
            return;

        try {
            this.queue.put(bytes);
            this.size += bytes.length;
        }catch(Exception e) {
            logger.debug(e.getMessage());
        }
    }

    /**
     * Pop one byte[] from queue, update size accordingly
     * @return
     */
    public byte[] pull() {
        byte[] retval = this.queue.poll();
        if (retval != null)
            this.size -= retval.length;

        return retval;
    }

    public long size() {
        return this.size;
    }
}