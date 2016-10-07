package com.jiananwang.iot.parser;

import com.jiananwang.iot.parser.model.ImpinjLabelResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

/**
 * Created by root on 16-10-7.
 */
public class TestImpinjResultParser {
//
//    @Test
//    public void high6() {
//        int result = ImpinjResultParser.high6((byte)0b11111100);
//        Assert.isTrue(result == 63);
//
//        result = ImpinjResultParser.high6((byte)0b00000100);
//        Assert.isTrue(result == 1);
//
//        result = ImpinjResultParser.high6((byte)0b11111111);
//        Assert.isTrue(result == 63);
//
//        result = ImpinjResultParser.high6((byte)0b00000111);
//        Assert.isTrue(result == 1);
//
//        result = ImpinjResultParser.high6((byte)0b00000000);
//        Assert.isTrue(result == 0);
//    }
//
//    @Test
//    public void low2() {
//        int result = ImpinjResultParser.low2((byte)0b11111100);
//        Assert.isTrue(result == 0);
//
//        result = ImpinjResultParser.low2((byte)0b00000100);
//        Assert.isTrue(result == 0);
//
//        result = ImpinjResultParser.low2((byte)0b11111111);
//        Assert.isTrue(result == 3);
//
//        result = ImpinjResultParser.low2((byte)0b00000111);
//        Assert.isTrue(result == 3);
//
//        result = ImpinjResultParser.low2((byte)0b00000000);
//        Assert.isTrue(result == 0);
//    }

    @Test
    public void testParse() {
        byte[] b = new byte[]{ (byte)0xA0, (byte)0x19, (byte)0x01, (byte)0x90, (byte)0x00, (byte)0x02, (byte)0x10, (byte)0x34, (byte)0x00, (byte)0x12, (byte)0x34, (byte)0x56, (byte)0x78, (byte)0x99, (byte)0x99, (byte)0x55, (byte)0x55, (byte)0x22, (byte)0x22, (byte)0x11, (byte)0x11, (byte)0x53, (byte)0xF5, (byte)0x51, (byte)0x8B, (byte)0x52, (byte)0xA4 };
        ImpinjLabelResult result = ImpinjResultParser.parse(b);


    }
}
