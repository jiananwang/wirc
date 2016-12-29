package com.jiananwang.iot.service;

import com.jiananwang.iot.constant.ImpinjCommands;
import com.jiananwang.iot.constant.ImpinjErrors;
import com.jiananwang.iot.parser.ImpinjResultParser;
import com.jiananwang.iot.parser.model.ImpinjLabelResult;
import com.jiananwang.iot.registery.GlobalRegistry;
import com.jiananwang.iot.service.queue.LocalRWQueueService;
import com.jiananwang.iot.util.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by root on 16-9-24.
 */
@Service
@PropertySource("application.properties")
public class YukeCommandService implements Runnable {
    private Logger logger = LoggerFactory.getLogger(YukeCommandService.class);

    private ApplicationContext appContext;

    // The time thread sleep between 2 uploads
    @Value("${wircUploadInterval}") private int uploadInterval;

    // Anything start with A0 goes to upload Queue first
    private Queue<ArrayList<Byte>> uploadQueue = new LinkedBlockingDeque<>();

    private ArrayList<Byte> currentList = null;



    private LocalRWQueueService bytesQueueService;
    private GlobalRegistry globalRegistry;



    public YukeCommandService(ApplicationContext context) {
        this.appContext = context;
    }




    @Override
    public void run() {
        bytesQueueService = (LocalRWQueueService)appContext.getBean("localRWQueueService");
        globalRegistry = (GlobalRegistry)appContext.getBean("globalRegistry");

        while (true) {
            if (bytesQueueService != null) {
                while (bytesQueueService.size() > 0) {
                    byte b = bytesQueueService.poll();
                    if (b == (byte)0x0A) {
                        // Get 0xA0, then flush current
                        populate();

                        // Here comes new output
                        currentList = new ArrayList<Byte>();
                        currentList.add(b); // 0x0A
                        continue;
                    }
                    if (currentList != null) {
                        currentList.add(b);

                        // Working on current input
                        if (currentList.size() == 1) { // 0xA0
                            continue;
                        }

                        if (currentList.size() > 1) { // 0xA0 SIZE
                            int size = currentList.get(1);
                            if (currentList.size() >= size + 2) {
                                populate();
                                continue;
                            }
                        }
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

    public void populate() {
        if (currentList != null && currentList.size() > 0) {
            if (currentList.size() > 5) { // something meaningful
                StringBuilder sb = new StringBuilder();
                for(byte b : currentList) {
                    sb.append(String.format("%02X ", b));
                }
                logger.info(sb.toString());
            }

        }
        currentList = null;
    }
}
