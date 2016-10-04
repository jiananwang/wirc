package com.jiananwang.iot.registery;

import org.springframework.stereotype.Component;

/**
 * Created by root on 16-10-4.
 */
@Component
public class GlobalRegistry {
    private int antenna = -1;

    private int defaultAntenna = 3;

    public int getAntenna() {
        return antenna;
    }

    public void setAntenna(int antenna) {
        this.antenna = antenna;
    }

    public int getDefaultAntenna() {
        return defaultAntenna;
    }

    public void setDefaultAntenna(int defaultAntenna) {
        this.defaultAntenna = defaultAntenna;
    }
}
