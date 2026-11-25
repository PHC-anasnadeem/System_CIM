package com.phc.cimplus.DataElements;

import java.io.Serializable;

public class CurrentUC implements Serializable {
    private int UC_Boundary_ID;
    private int UC_Code;
    private int UC_NO;
    private String UC_Name = "";
    private String UC_Key = "";
    private String Type = "";
    private String Tehsil_N = "";
    private String District_N = "";
    private String Division_N = "";
    private String DisplayName = "";

    public CurrentUC() {
    }

    public int getUC_Boundary_ID() {
        return UC_Boundary_ID;
    }

    public void setUC_Boundary_ID(int UC_Boundary_ID) {
        this.UC_Boundary_ID = UC_Boundary_ID;
    }

    public int getUC_Code() {
        return UC_Code;
    }

    public void setUC_Code(int UC_Code) {
        this.UC_Code = UC_Code;
    }

    public int getUC_NO() {
        return UC_NO;
    }

    public void setUC_NO(int UC_NO) {
        this.UC_NO = UC_NO;
    }

    public String getUC_Name() {
        return UC_Name;
    }

    public void setUC_Name(String UC_Name) {
        this.UC_Name = UC_Name;
    }

    public String getUC_Key() {
        return UC_Key;
    }

    public void setUC_Key(String UC_Key) {
        this.UC_Key = UC_Key;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTehsil_N() {
        return Tehsil_N;
    }

    public void setTehsil_N(String tehsil_N) {
        Tehsil_N = tehsil_N;
    }

    public String getDistrict_N() {
        return District_N;
    }

    public void setDistrict_N(String district_N) {
        District_N = district_N;
    }

    public String getDivision_N() {
        return Division_N;
    }

    public void setDivision_N(String division_N) {
        Division_N = division_N;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }
}
