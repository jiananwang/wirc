package com.jiananwang.iot.parser.model;

/**
 * Created by root on 16-10-7.
 */
public class ImpinjLabelResult {
    private String PC;
    private String EPC;
    private String CRC;
    private int RSSI;
    private int Frequent;
    private int Antenna;
    private int InvCount;

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %d | %d | %d | %d", PC, EPC, CRC, RSSI, Frequent, Antenna, InvCount);
    }

    public String getPC() {
        return PC;
    }

    public void setPC(String PC) {
        this.PC = PC;
    }

    public String getEPC() {
        return EPC;
    }

    public void setEPC(String EPC) {
        this.EPC = EPC;
    }

    public String getCRC() {
        return CRC;
    }

    public void setCRC(String CRC) {
        this.CRC = CRC;
    }

    public int getRSSI() {
        return RSSI;
    }

    public void setRSSI(int RSSI) {
        this.RSSI = RSSI;
    }

    public int getFrequent() {
        return Frequent;
    }

    public void setFrequent(int frequent) {
        Frequent = frequent;
    }

    public int getAntenna() {
        return Antenna;
    }

    public void setAntenna(int antenna) {
        Antenna = antenna;
    }

    public int getInvCount() {
        return InvCount;
    }

    public void setInvCount(int invCount) {
        InvCount = invCount;
    }
}
