package com.jiananwang.iot.constant;

/**
 * Created by root on 16-12-27.
 */
public class YukeCommands {
    public static byte[] readLabel() {
        // A003FF312D
        return new byte[]{(byte) 0xA0, 0x03, (byte) 0xFF, 0x31, 0x2D};
    }
}
