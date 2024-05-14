package com.phc.cim.DataElements;


public class subActionType {


    public String getSubActionType_Id() {
        return subActionType_Id;
    }

    public void setSubActionType_Id(String subActionType_Id) {
        this.subActionType_Id = subActionType_Id;
    }



    String subActionType_Id;

    public String getSubcatActionType_Id() {
        return subcatActionType_Id;
    }

    public void setSubcatActionType_Id(String subcatActionType_Id) {
        this.subcatActionType_Id = subcatActionType_Id;
    }

    String subcatActionType_Id;

    public String getSubactionType() {
        return subactionType;
    }

    public void setSubactionType(String subactionType) {
        this.subactionType = subactionType;
    }

    String subactionType;

    public String getSubRoleID() {
        return SubRoleID;
    }

    public void setSubRoleID(String subRoleID) {
        SubRoleID = subRoleID;
    }

    public String getSubStatusID() {
        return SubStatusID;
    }

    public void setSubStatusID(String subStatusID) {
        SubStatusID = subStatusID;
    }

    String SubRoleID, SubStatusID;
   public subActionType(String subActionType_Id, String subcatActionType_Id, String subactionType ,String SubRoleID,String SubStatusID){
       this.subActionType_Id=subActionType_Id;
       this.subcatActionType_Id=subcatActionType_Id;
       this.SubRoleID=SubRoleID;
       this.SubStatusID=SubStatusID;
    this.subactionType=subactionType;

   }
}
