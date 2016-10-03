package com.jiananwang.iot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * Created by root on 16-9-24.
 */
@Service
@PropertySource("application.properties")
public class UploadService implements Runnable {
    private ApplicationContext appContext;

    @Value("${wircUploadInterval}") private int uploadInterval;



    public UploadService(ApplicationContext context) {
        this.appContext = context;
    }

    @Override
    public void run() {
        while (true) {
            BytesQueueService bytesQueueService = (BytesQueueService)appContext.getBean("bytesQueueService");
            if (bytesQueueService != null) {
                if (bytesQueueService.size() > 100) {
                    while (bytesQueueService.size() > 0) {
                        bytesQueueService.poll();
                        //System.out.println();
                        System.out.println(" queue size: " + bytesQueueService.size());
                    }
                }
            }

            try {
                Thread.sleep(uploadInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
