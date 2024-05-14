package com.phc.cim.DataElements;


public class SectorType {


    public String getSectorType() {
        return sectorType;
    }

    public void setSectorType(String sectorType) {
        this.sectorType = sectorType;
    }

    String sectorType;

    public String getSectorType_id() {
        return sectorType_id;
    }

    public void setSectorType_id(String sectorType_id) {
        this.sectorType_id = sectorType_id;
    }

    String sectorType_id;
   public SectorType(String sectorType_id,String sectorType){
       this.sectorType_id=sectorType_id;
    this.sectorType=sectorType;

   }
}
