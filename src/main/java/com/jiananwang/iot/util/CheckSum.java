package com.jiananwang.iot.util;

/**
 * Created by root on 16-10-4.
 */
public class CheckSum {
    public static byte impinjCheckSum(byte[] uBuff, int uBuffLen)
    {
        int uSum=0;
        for(int i=0;i<uBuffLen;i++)
        {
            uSum = uSum + uBuff[i];
        }
        uSum = (~uSum) + 1;

        return (byte)uSum;
    }
    public static String impinjCheckSumHex(byte[] uBuff, int uBuffLen)
    {
        int uSum=0;
        for(int i=0;i<uBuffLen;i++)
        {
            uSum = uSum + uBuff[i];
        }
        uSum = (~uSum) + 1;

        return Integer.toHexString( impinjCheckSum(uBuff, uBuffLen) & 0xff );
    }
}
