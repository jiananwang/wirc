package com.jiananwang.iot.biz.publisher;

import com.jiananwang.iot.constant.ImpinjCommands;
import com.jiananwang.iot.constant.ImpinjErrors;
import com.jiananwang.iot.parser.ImpinjResultParser;
import com.jiananwang.iot.parser.model.ImpinjLabelResult;
import com.jiananwang.iot.registery.GlobalRegistry;
import com.jiananwang.iot.biz.publisher.model.UploadChamber;
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
public class ImpinjCommandService implements Runnable {
    private Logger logger = LoggerFactory.getLogger(ImpinjCommandService.class);

    private ApplicationContext appContext;

    // The time thread sleep between 2 uploads
    @Value("${wircUploadInterval}") private int uploadInterval;

    // Anything start with A0 goes to upload Queue first
    private Queue<ArrayList<Byte>> uploadQueue = new LinkedBlockingDeque<>();

    private ArrayList<Byte> currentList = null;



    private LocalRWQueueService bytesQueueService;
    private GlobalRegistry globalRegistry;


    // when the chamber is full then make upload
    private UploadChamber<ImpinjLabelResult> uploadChamber;


    public ImpinjCommandService(ApplicationContext context) {
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
                    if (b == (byte)0xA0) {
                        // Get 0xA0, then flush current
                        populate();

                        // Here comes new output
                        currentList = new ArrayList<Byte>();
                        currentList.add(b); // 0xA0
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
                // Antenna OK (0x74)
                if (currentList.get(3) == ImpinjCommands.SET_ANTENNA && currentList.get(4) == ImpinjErrors.SUCCESS) {
                    globalRegistry.setAntenna(globalRegistry.getDefaultAntenna());
                    logger.debug(String.format("[ImpinjCommandService: populate] antenna set: %1s", globalRegistry.getAntenna()));
                }

                // Read inventory buffer done (0x91)
                if (currentList.get(3) == ImpinjCommands.INVENTORY_BUFFER_RESET && currentList.get(1) > 4){

                    byte[] len = new byte[] {currentList.get(4), currentList.get(5)};
                    int count = ByteUtil.byteArrayToInt(len);
                    logger.debug("..................... label count: " + count);

                    ImpinjLabelResult result = ImpinjResultParser.parse(ByteUtil.toPrimitive( currentList ));


                    {
                        // misc check and upload
                        if (this.uploadChamber == null) {
                            this.uploadChamber = new UploadChamber(count);
                        }

                        if (!this.uploadChamber.isFull()) {
                            this.uploadChamber.add(result);
                        }

                        if (this.uploadChamber.isFull()) {
                            // TODO: upload

                            logger.debug(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>> upload >>>>>>>>>>>>>>>>>>>");
                            this.uploadChamber = null;
                        }
                    }

                    logger.debug(result.toString());
//                    this.uploadQueue.add(currentList);
                }
            }
            logger.debug(String.format("[ImpinjCommandService: populate] size: %1s", currentList.size()));

        }
        currentList = null;
    }
}
