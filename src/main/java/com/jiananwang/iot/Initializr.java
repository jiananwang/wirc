package com.jiananwang.iot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jiananwang.iot.serial.SerialPortRW;

@Component
@Scope("prototype")
public class Initializr {
	@Autowired
	private SerialPortRW serialProtRW;
	
	
	public void init() {
		try {
			serialProtRW.connect("/dev/ttyUSB0");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
