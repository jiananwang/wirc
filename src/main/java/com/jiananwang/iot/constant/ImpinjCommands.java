package com.jiananwang.iot.constant;

/**
 * Created by root on 16-10-4.
 */
public class ImpinjCommands {
    public static final byte SET_ANTENNA = (byte) 0x74;

    /**
     * Set current working antenna
     * @return
     */
    public static byte[] newSetAntenna() {
        return new byte[]{(byte) 0xA0, 0x04, 0x01, (byte) 0x74, 0x03, 0x00};
    }

    /**
     * Read fron device into buffer
     * @return
     */
    public static byte[] newReadIntoBuffer() {
        return new byte[] { (byte)0xA0, 0x04, 0x01, (byte)0x80, 0x0A, (byte)0xD1 };
    }

    public static byte[] newReadFromBuffer() {
        return new byte[] { (byte)0xA0, 0x03, 0x01, (byte)0x90, (byte)0xCC};
    }
}
