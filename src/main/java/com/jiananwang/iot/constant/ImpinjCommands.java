package com.jiananwang.iot.constant;

/**
 * Created by root on 16-10-4.
 */
public class ImpinjCommands {
    public static final byte SET_ANTENNA = (byte) 0x74;

    public static final byte INVENTORY_BUFFER = (byte) 0x90;

    public static final byte INVENTORY_BUFFER_RESET = (byte) 0x91;


    public enum BEEPER_MODE {SILENCE, EACH_BATCH, EACH_LABEL}

    /**
     * Set current working antenna
     *
     * @return
     */
    public static byte[] newSetAntenna() {
        return new byte[]{(byte) 0xA0, 0x04, 0x01, (byte) 0x74, 0x03, 0x00};
    }

    /**
     * Read antenna data into device buffer
     *
     * @return
     */
    public static byte[] newReadIntoBuffer() {
        return new byte[]{(byte) 0xA0, 0x04, 0x01, (byte) 0x80, 0x0A, (byte) 0xD1};
    }

    /**
     * Read data from device buffer
     *
     * @return
     */
    public static byte[] newReadFromBuffer() {
        return new byte[]{(byte) 0xA0, 0x03, 0x01, (byte) 0x90, (byte) 0xCC};
    }

    /**
     * Read data from device buffer, and reset buffer right after
     *
     * @return
     */
    public static byte[] newReadFromBuffer_Reset() {
        return new byte[]{(byte) 0xA0, 0x03, 0x01, (byte) 0x91, (byte) 0xCB};
    }

    /**
     * Set beeper mode: 0-silent; 1-each batch; 2-each label
     *
     * @param mode
     * @return
     */
    public static byte[] newSetBeeper(BEEPER_MODE mode) {
        return new byte[]{(byte) 0xA0, 0x04, (byte) 0xFF, 0x7A, (byte) mode.ordinal(), (byte) 0xE3};
    }
}
