package com.phc.cim.DataElements;


public class UpdateStatL2 {


    public String getUpdatedStatL2() {
        return updatedStatL2;
    }

    public void setUpdatedStatL2(String updatedStatL2) {
        this.updatedStatL2 = updatedStatL2;
    }

    String updatedStatL2;


    public String getUpdatedStatL2_Id() {
        return updatedStatL2_Id;
    }

    public void setUpdatedStatL2_Id(String updatedStatL2_Id) {
        this.updatedStatL2_Id = updatedStatL2_Id;
    }

    String updatedStatL2_Id;
   public UpdateStatL2(String updatedStatL2_Id, String updatedStatL2){
       this.updatedStatL2_Id=updatedStatL2_Id;
    this.updatedStatL2=updatedStatL2;

   }
}
