package com.phc.cim.DataElements;


public class PWSOrgType {


    public String getOrgType() {
        return PWSorg;
    }

    public void setOrgType(String PWSorg) {
        this.PWSorg = PWSorg;
    }

    String PWSorg;

    public String getOrgType_id() {
        return PWSorgID;
    }

    public void setOrgType_id(String PWSorgID) {
        this.PWSorgID = PWSorgID;
    }

    String PWSorgID;
   public PWSOrgType(String PWSorgID, String PWSorg){
       this.PWSorgID=PWSorgID;
    this.PWSorg=PWSorg;

   }
}
