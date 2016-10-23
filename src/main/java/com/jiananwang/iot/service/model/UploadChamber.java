package com.jiananwang.iot.service.model;

import com.jiananwang.iot.parser.model.ImpinjLabelResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16-10-23.
 */
public class UploadChamber<T> {
    private int capacity;
    private List<T> uploadList = new ArrayList();


    public UploadChamber (int capacity) {
        this.capacity = capacity;
    }

    public boolean isFull() {
        if (this.uploadList == null)
            return false;

        if (this.uploadList.size() < capacity)
            return false;

        return true;
    }

    public void add (T t) {
        if (this.uploadList == null)
            this.uploadList = new ArrayList<T>();

        this.uploadList.add(t);
    }











    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<T> getUploadList() {
        return uploadList;
    }

    public void setUploadList(List<T> uploadList) {
        this.uploadList = uploadList;
    }
}
