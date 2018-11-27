package com.csulb.edu.geometry;

public class Point {

    private double longitude;
    private double latitude;

    public Point(double lng,double lat){
        longitude = lng;
        latitude = lat;
    }

    @Override
    public String toString(){
     return " Long :"+ longitude +" , lat :"+latitude+" ";
    }

    public double getLongitude(){return  longitude;}
    public double getLatitude(){return latitude;}
}
