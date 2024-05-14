package com.phc.cim.DataElements;

/**
 * Created by Admin on 4/21/2018.
 */

public class PieEntry {


    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    float value;
    String label;
    public PieEntry(float value, String label) {
        this.value=value;
        this.label=label;

    }
}
