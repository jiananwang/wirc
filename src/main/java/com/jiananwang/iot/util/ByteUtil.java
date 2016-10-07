package com.jiananwang.iot.util;

import java.nio.ByteBuffer;

/**
 * Created by root on 16-10-7.
 */
public class ByteUtil {
    /**
     * byte[]->int
     * @param bytes
     * @return
     */
    public static int byteArrayToInt(byte[] bytes) {
        ByteBuffer wrapped = ByteBuffer.wrap(bytes); // big-endian by default
        short num = wrapped.getShort();
        return num;
    }
}
