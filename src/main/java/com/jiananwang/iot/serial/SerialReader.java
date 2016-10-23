package com.jiananwang.iot.serial;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import com.jiananwang.iot.biz.publisher.LocalRWQueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;

@Configurable
public class SerialReader implements Runnable {
	Logger logger = LoggerFactory.getLogger(SerialReader.class);

	private ApplicationContext appContext;
	
	private InputStream in;

	public SerialReader(ApplicationContext appContext, InputStream in) {
		this.appContext = appContext;
		this.in = in;
	}
	

	@Override
	public void run() {
		byte[] buffer = new byte[1024];
		int len = -1;
		try {
			while ((len = this.in.read(buffer)) > -1) {
				byte[] dst = Arrays.copyOf(buffer, len);
				put(dst);
				//logger.debug("[READER] -> " + String.valueOf(Hex.encodeHex(dst)));

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send data to queue
	 * @param bytes
	 */
	private void put(byte[] bytes) {
		LocalRWQueueService bytesQueueService = (LocalRWQueueService)appContext.getBean("localRWQueueService");
		if (bytesQueueService != null) {
			bytesQueueService.put(bytes);
		} else {
			logger.error("cannot get application context");
		}
	}
}
