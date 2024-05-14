package com.phc.cim.DataElements;


public class District {

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    String district;

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    String district_id;

    public String getDistrict_div() {
        return district_div;
    }

    public void setDistrict_div(String district_div) {
        this.district_div = district_div;
    }

    String district_div;
   public District(String district_id,String district_div, String district){
       this.district_div=district_div;
       this.district_id=district_id;
    this.district=district;


   }
}
