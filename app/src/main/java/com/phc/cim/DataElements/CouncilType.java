package com.phc.cim.DataElements;


public class CouncilType {


    public String getCouncilType() {
        return councilType;
    }

    public void setCouncilType(String councilType) {
        this.councilType = councilType;
    }

    String councilType;

    public String getSectorType_id() {
        return sectorType_id;
    }

    public void setSectorType_id(String sectorType_id) {
        this.sectorType_id = sectorType_id;
    }

    String sectorType_id;
   public CouncilType(String sectorType_id, String councilType){
       this.sectorType_id=sectorType_id;
    this.councilType=councilType;

   }
}
