package com.jiananwang.iot.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestReceiverService {
    @Autowired private ReceiverService receiverService;

    @Test
    public void test() {
        receiverService.put(new byte[]{0x00, 0x01});
        receiverService.put(new byte[]{0x00, 0x01, 0x02});

        Assert.isTrue(receiverService.size() == 5);

        receiverService.pull();

        Assert.isTrue(receiverService.size() == 3);
    }
}
