package com.jiananwang.iot.serial;

import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

@Service
public class SerialPortRW {
	private Logger logger = LoggerFactory.getLogger(SerialPortRW.class);

	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	
	public void connect(String portName) throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned()) {
			logger.error("Error: Port is currently in use");
		} else {
			int timeout = 2000;
			CommPort commPort = portIdentifier.open(this.getClass().getName(), timeout);

			if (commPort instanceof SerialPort) {
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);

				InputStream in = serialPort.getInputStream();
				OutputStream out = serialPort.getOutputStream();

//				(new Thread(new SerialReader(in))).start();
//				(new Thread(new SerialWriter(out))).start();
				startRWThread(in, out);

			} else {
				logger.error("Error: Only serial ports are handled by this example.");
			}
		}
	}
	
	private void startRWThread(InputStream in, OutputStream out) {
//	    ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) appContext.getBean("taskExecutor");
	    SerialWriter writeTask = new SerialWriter(appContext, out);
	    SerialReader readTask = new SerialReader(appContext, in);

	    taskExecutor.execute(readTask);
	    taskExecutor.execute(writeTask);
	}
}
