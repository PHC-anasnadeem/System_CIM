package com.phc.cimplus.DataElements;

public class UC {
    String ucId;
    String district;
    String tehsil;
    String ucName;

    public String getUcId() { return ucId; }
    public void setUcId(String ucId) { this.ucId = ucId; }
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    public String getTehsil() { return tehsil; }
    public void setTehsil(String tehsil) { this.tehsil = tehsil; }
    public String getUcName() { return ucName; }
    public void setUcName(String ucName) { this.ucName = ucName; }

    public UC(String ucId, String district, String tehsil, String ucName) {
        this.ucId = ucId;
        this.district = district;
        this.tehsil = tehsil;
        this.ucName = ucName;
    }
}
