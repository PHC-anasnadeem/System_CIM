package com.phc.cim.DataElements;


public class RegStatus {


    public String getRegStatus() {
        return RegStatus;
    }

    public void setRegStatus(String regStatus) {
        RegStatus = regStatus;
    }

    String RegStatus;


    public String getRegStatus_id() {
        return RegStatus_id;
    }

    public void setRegStatus_id(String regStatus_id) {
        RegStatus_id = regStatus_id;
    }

    String RegStatus_id;

   public RegStatus(String RegStatus_id, String RegStatus){
       this.RegStatus_id=RegStatus_id;
    this.RegStatus=RegStatus;


   }
}
