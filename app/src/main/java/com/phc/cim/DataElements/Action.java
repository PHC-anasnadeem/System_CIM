package com.phc.cim.DataElements;


public class Action {


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    String action;


    public String getAction_id() {
        return action_id;
    }

    public void setAction_id(String action_id) {
        this.action_id = action_id;
    }

    String action_id;

   public Action(String action_id, String action){
       this.action_id=action_id;
    this.action=action;


   }
}
