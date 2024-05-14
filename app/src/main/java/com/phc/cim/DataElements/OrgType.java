package com.phc.cim.DataElements;


public class OrgType {


    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    String orgType;

    public String getOrgType_id() {
        return orgType_id;
    }

    public void setOrgType_id(String orgType_id) {
        this.orgType_id = orgType_id;
    }

    String orgType_id;
   public OrgType(String orgType_id, String orgType){
       this.orgType_id=orgType_id;
    this.orgType=orgType;

   }
}
