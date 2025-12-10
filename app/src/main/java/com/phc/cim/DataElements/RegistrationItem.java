package com.phc.cim.DataElements;

public class RegistrationItem {

    private String Registration_Number;
    private String HCE_Name;
    private String HCE_License_Type;
    private String HCE_District;
    private String Registration_Date;

    public RegistrationItem(String registration_Number, String HCE_Name, String HCE_License_Type, String HCE_District, String registration_Date) {
        Registration_Number = registration_Number;
        this.HCE_Name = HCE_Name;
        this.HCE_License_Type = HCE_License_Type;
        this.HCE_District = HCE_District;
        Registration_Date = registration_Date;
    }

    public String getRegistrationNumber() {
        return Registration_Number;
    }

    public String getHceName() {
        return HCE_Name;
    }

    public String getHceLicenseType() {
        return HCE_License_Type;
    }

    public String getHceDistrict() {
        return HCE_District;
    }

    public String getRegistrationDate() {
        return Registration_Date;
    }
}
