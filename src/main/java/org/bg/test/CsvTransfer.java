package org.bg.test;

import org.bg.test.sensors.AbstractSensorInputPojo;

import java.util.ArrayList;
import java.util.List;

public class CsvTransfer {
    private List<AbstractSensorInputPojo> csvList;

    public void setCsvList(List<AbstractSensorInputPojo> csvList) {
        this.csvList = csvList;
    }

    public List<AbstractSensorInputPojo> getCsvList() {
        if (csvList != null)
            return csvList;
        return new ArrayList<>();
    }
}
