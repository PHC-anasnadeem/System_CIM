package com.phc.cim.DataElements;


public class UpdateStatL1 {


    public String getUpdatedStatL1_Id() {
        return updatedStatL1_Id;
    }

    public void setUpdatedStatL1_Id(String updatedStatL1_Id) {
        this.updatedStatL1_Id = updatedStatL1_Id;
    }

    String updatedStatL1_Id;

    public String getUpdatedStatL1() {
        return updatedStatL1;
    }

    public void setUpdatedStatL1(String updatedStatL1) {
        this.updatedStatL1 = updatedStatL1;
    }

    String updatedStatL1;
   public UpdateStatL1(String updatedStatL1_Id, String updatedStatL1){
       this.updatedStatL1_Id=updatedStatL1_Id;
    this.updatedStatL1=updatedStatL1;

   }
}
