package com.phc.cim.DataElements;


public class Tehsil {


    public String getTdistrict() {
        return Tdistrict;
    }

    public void setTdistrict(String tdistrict) {
        Tdistrict = tdistrict;
    }



    public String getTehsil() {
        return tehsil;
    }

    public void setTehsil(String tehsil) {
        this.tehsil = tehsil;
    }
    String Tdistrict;
    String tehsil;

    public String getTehsil_id() {
        return Tehsil_id;
    }

    public void setTehsil_id(String tehsil_id) {
        Tehsil_id = tehsil_id;
    }

    String Tehsil_id;
    public Tehsil(String Tehsil_id, String Tdistrict, String tehsil){
        this.Tehsil_id=Tehsil_id;
    this.Tdistrict=Tdistrict;
    this.tehsil=tehsil;
   }
}
