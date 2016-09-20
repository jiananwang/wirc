package com.jiananwang.iot.serial;

import java.io.IOException;
import java.io.OutputStream;

public class SerialWriter implements Runnable {

	OutputStream out;

	public SerialWriter(OutputStream out) {
		this.out = out;
	}

	@Override
	public void run() {
		try {
			while (true) {
				// byte[] b = new byte[] { (byte) 0xA0, 0x03, (byte) 0xFF, 0x00,
				// 0x5E };

				byte[] b = new byte[] { (byte) 0xA0, 0x03, (byte) 0xFF, 0x31, 0x2D };

				// byte[] b = new byte[] { (byte) 0xA0, 0x05, (byte) 0xFF, 0x11,
				// 0x04, 0x01, 0x46 };
				this.out.write(b);

				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
