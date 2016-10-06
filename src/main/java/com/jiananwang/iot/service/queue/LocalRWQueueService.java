package com.jiananwang.iot.service.queue;

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
public class LocalRWQueueService {
    private Logger logger = LoggerFactory.getLogger(LocalRWQueueService.class);

    /**
     * Thread safe blocking queue for read and write. Note it receives byte[] instead of byte.
     */
    private BlockingQueue<Byte> queue = new LinkedBlockingDeque<>();


    /**
     * Add byte[] into queue, update size accordingly.
     * @param bytes Incoming byte[]
     */
    public void put(byte[] bytes) {
        if (bytes == null)
            return;

        try {
            for(byte b : bytes)
                this.queue.put(b);
        }catch(Exception e) {
            logger.debug(e.getMessage());
        }
    }

    /**
     * Pop one byte[] from queue, update size accordingly
     * @return Popped byte[]
     */
    public Byte poll() {
        return this.queue.poll();
    }

    public int size() {
        return this.queue.size();
    }

    public byte peek() {
        return this.queue.peek();
    }

}
