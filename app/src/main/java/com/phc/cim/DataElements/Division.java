package com.phc.cim.DataElements;


public class Division {


    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDivision_id() {
        return division_id;
    }

    public void setDivision_id(String division_id) {
        this.division_id = division_id;
    }

    String division;
    String division_id;

   public Division(String division_id, String division){
       this.division_id=division_id;
    this.division=division;


   }
}
