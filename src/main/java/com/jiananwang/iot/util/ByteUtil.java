package com.jiananwang.iot.util;

import java.nio.ByteBuffer;
import java.util.List;

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

    public static byte[] toPrimitive(List<Byte> list) {
        byte[] retval = new byte[list.size()];
        for(int i=0; i<list.size(); i++) {
            retval[i] = list.get(i);
        }
        return retval;
    }
}
