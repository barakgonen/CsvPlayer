package org.bg.test;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleField {
    private String fieldName;
    private ArrayList<String> reports;

    public SimpleField(String fieldName) {
        this.fieldName = fieldName;
        this.reports = new ArrayList<>();
    }

    public void addField(String value) {
        reports.add(value);
    }

    public String getFieldName() {
        return fieldName;
    }

    public int numberOfReports() {
        return reports.size();
    }

    public String getNthReport(int n) {
        return reports.get(n);
    }
}
