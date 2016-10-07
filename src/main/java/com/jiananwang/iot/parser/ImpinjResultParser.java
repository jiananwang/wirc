package com.jiananwang.iot.parser;

import com.jiananwang.iot.parser.model.ImpinjLabelResult;
import com.jiananwang.iot.util.ByteUtil;
import org.apache.commons.codec.binary.Hex;

import java.util.Arrays;

/**
 * Created by root on 16-10-7.
 */
public class ImpinjResultParser {
    public static int tagCount(byte[] in) {
        if (in != null && in.length > 5) {
            return ByteUtil.byteArrayToInt(Arrays.copyOfRange(in, 4, 2));
        }
        return 0;
    }

    public static ImpinjLabelResult parse(byte[] in) {
        int data_len = in[6];

        ImpinjLabelResult retval = new ImpinjLabelResult();
        retval.setPC(String.valueOf(Hex.encodeHex(Arrays.copyOfRange(in, 7, 9))));
        retval.setEPC(String.valueOf(Hex.encodeHex(Arrays.copyOfRange(in, 9, 9 + data_len - 4))));
        retval.setCRC(String.valueOf(Hex.encodeHex(Arrays.copyOfRange(in, 7 + data_len - 2, 7 + data_len))));
        retval.setRSSI(in[7 + data_len]);
        int combo = in[7 + data_len + 1];
        retval.setFrequent(high6(combo));
        retval.setAntenna(low2(combo));
        retval.setInvCount(in[7 + data_len + 2]);
        return retval;
    }

    private static int high6(int b) {
        int newb = b >> 2;
        return newb & (int)0b00111111;
    }
    private static int low2(int b) {
        return b & (int)0b00000011;
    }
}
