package com.jiananwang.iot.service.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by root on 16-10-7.
 */
@Service
public class UploadQueueService {
    private Logger logger = LoggerFactory.getLogger(LocalRWQueueService.class);

    private BlockingQueue<Byte> queue = new LinkedBlockingDeque<>();

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

    public Byte poll() {
        return this.queue.poll();
    }
}
