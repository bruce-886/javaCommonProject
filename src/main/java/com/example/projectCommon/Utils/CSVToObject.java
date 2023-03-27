package com.example.projectCommon.Utils;

import java.util.ArrayList;
import java.util.List;

public class CSVToObject<T> {

    private List<String[]> csvStringList;

    private List<T> csvList;

    public CSVToObject() {
    }

    public List<String[]> getCsvStringList() {
        if (csvStringList != null) {
            return csvStringList;
        }
        return new ArrayList<>();
    }

    public List<T> getCsvList() {
        if (csvList != null) {
            return csvList;
        }
        return new ArrayList<>();
    }

    public void setCsvStringList(List<String[]> csvStringList) {
        this.csvStringList = csvStringList;
    }

    public void setCsvList(List<T> csvList) {
        this.csvList = csvList;
    }

    public void addLine(String[] line) {
        if (this.csvList == null) {
            this.csvStringList = new ArrayList<>();
        }
        this.csvStringList.add(line);
    }

}
