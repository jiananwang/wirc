package com.jiananwang.iot.serial;

import java.io.IOException;
import java.io.OutputStream;

import com.jiananwang.iot.registery.GlobalRegistry;
import com.jiananwang.iot.util.CheckSum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

@Configurable
public class SerialWriter implements Runnable {
	private ApplicationContext appContext;
	
	private OutputStream out;

	private GlobalRegistry globalRegistry;

	public SerialWriter(ApplicationContext appContext, OutputStream out) {
		this.appContext = appContext;
		this.out = out;
	}
	
//	public void init(OutputStream out) {
//		this.out = out;
//	}

	@Override
	public void run() {
		ApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		globalRegistry = (GlobalRegistry)appContext.getBean("globalRegistry");

		System.out.println("--------------------------------");
		System.out.println(appContext == null);
		
		
		try {
			while (true) {
				// byte[] b = new byte[] { (byte) 0xA0, 0x03, (byte) 0xFF, 0x00,
				// 0x5E };

//				byte[] b = new byte[] { (byte) 0xA0, 0x03, (byte) 0xFF, 0x31, 0x2D };

				// byte[] b = new byte[] { (byte) 0xA0, 0x05, (byte) 0xFF, 0x11,
				// 0x04, 0x01, 0x46 };



				// try to send the command to read label
				try {
					// 1. set antenna
					if (globalRegistry.getAntenna() < 0) {
						byte[] b = new byte[] { (byte)0xA0, 0x04, 0x01, (byte)0x74, 0x03, 0x00 };
//						byte[] b = new byte[] { (byte)0xA0, 0x04, 0x01, (byte)0x80, 0x0A, (byte)0xD1 };
						byte check_sum = CheckSum.impinjCheckSum(b, 5);
						b[5] = check_sum;

						this.out.write(b);

						Thread.sleep(2000);

						continue;
					}

					byte[] b = new byte[] { (byte)0xA0, 0x04, 0x01, (byte)0x80, 0x0A, (byte)0xD1 };
					this.out.write(b); System.out.println("read into buffer");

					Thread.sleep(5000);
					b = new byte[] { (byte)0xA0, 0x03, 0x01, (byte)0x90, (byte)0xCC};
					this.out.write(b); System.out.println("read from buffer");

					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
