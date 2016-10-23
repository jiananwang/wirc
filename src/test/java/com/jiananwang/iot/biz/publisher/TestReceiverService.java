package com.jiananwang.iot.biz.publisher;

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
    @Autowired private LocalRWQueueService bytesQueueService;

    @Test
    public void test() {
        bytesQueueService.put(new byte[]{0x00, 0x01});
        bytesQueueService.put(new byte[]{0x00, 0x01, 0x02});

        Assert.isTrue(bytesQueueService.size() == 5);

        bytesQueueService.poll();

        Assert.isTrue(bytesQueueService.size() == 3);
    }
}
