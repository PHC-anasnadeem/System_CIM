package com.phc.cim.DataElements;


public class Distance {


    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    String distance;


    public String getDistance_id() {
        return distance_id;
    }

    public void setDistance_id(String distance_id) {
        this.distance_id = distance_id;
    }

    String distance_id;

   public Distance(String distance_id, String distance){
       this.distance_id=distance_id;
    this.distance=distance;


   }
}
