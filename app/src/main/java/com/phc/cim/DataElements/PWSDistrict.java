package com.phc.cim.DataElements;


public class PWSDistrict {

    public String getDistrict() {
        return PWS_District;
    }

    public void setDistrict(String PWS_District) {
        this.PWS_District = PWS_District;
    }

    String PWS_District;

    public String getDistrict_id() {
        return pwsDistrict_Id;
    }

    public void setDistrict_id(String pwsDistrict_Id) {
        this.pwsDistrict_Id = pwsDistrict_Id;
    }

    String pwsDistrict_Id;

   public PWSDistrict(String pwsDistrict_Id, String PWS_District){

       this.pwsDistrict_Id=pwsDistrict_Id;
    this.PWS_District=PWS_District;


   }
}
