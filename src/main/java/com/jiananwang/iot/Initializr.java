package com.jiananwang.iot;

import com.jiananwang.iot.serial.SerialPortRW;
import com.jiananwang.iot.service.ImpinjCommandService;
import com.jiananwang.iot.service.YukeCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Initializr {
	@Autowired private SerialPortRW serialProtRW;

	@Autowired private TaskExecutor taskExecutor;

	@Autowired private ApplicationContext appContext;
	
	public void init() {
		try {
			serialProtRW.connect("/dev/ttyUSB0");
//			ImpinjCommandService service = new ImpinjCommandService(appContext);
			YukeCommandService service = new YukeCommandService(appContext);
			taskExecutor.execute(service);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
