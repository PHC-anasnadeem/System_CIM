package com.phc.cim.DataElements;


public class ActionType {


    public String getActionType_Id() {
        return actionType_Id;
    }

    public void setActionType_Id(String actionType_Id) {
        this.actionType_Id = actionType_Id;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    String actionType_Id;

    public String getFIRAssociated() {
        return FIRAssociated;
    }

    public void setFIRAssociated(String FIRAssociated) {
        this.FIRAssociated = FIRAssociated;
    }

    public String getCatDefinition() {
        return CatDefinition;
    }

    public void setCatDefinition(String catDefinition) {
        CatDefinition = catDefinition;
    }

    String FIRAssociated;
    String CatDefinition;

    String actionType;

    public String getCatRoleID() {
        return CatRoleID;
    }

    public void setCatRoleID(String catRoleID) {
        CatRoleID = catRoleID;
    }

    public String getCatStatusID() {
        return CatStatusID;
    }

    public void setCatStatusID(String catStatusID) {
        CatStatusID = catStatusID;
    }

    String CatRoleID,CatStatusID;
   public ActionType(String actionType_Id, String actionType, String FIRAssociated, String CatDefinition,String CatRoleID, String CatStatusID){
       this.actionType_Id=actionType_Id;
    this.actionType=actionType;
    this.FIRAssociated=FIRAssociated;
    this.CatDefinition=CatDefinition;
    this.CatRoleID=CatRoleID;
    this.CatStatusID=CatStatusID;

   }
}
