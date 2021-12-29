package org.bg.test;

import java.util.ArrayList;
import java.util.Collection;

public class SimpleField {
    private String fieldName;
    private Collection<String> reports;

    public SimpleField(String fieldName) {
        this.fieldName = fieldName;
        this.reports = new ArrayList<>();
    }
}
