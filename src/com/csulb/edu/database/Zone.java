package com.csulb.edu.database;

import com.csulb.edu.geometry.Point;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Zone implements DBObject {


    private long ID;
    private String name;
    private long squad_no;
    private List<Point> polygonVertices;


    //parses line to give out a zone object
    public Zone(String line) throws Exception{

        try{

            String[] zoneValues =line.split(",");

            long zoneID = Long.parseLong(zoneValues[0].trim());
            String zoneName = zoneValues[1].trim();
            long zoneSquad = Long.parseLong(zoneValues[2].trim());
            int numVertices = Integer.parseInt(zoneValues[3].trim());
            int currentValue = 4;
            ArrayList<Point> points = new ArrayList<>();
            while(numVertices>0){
                points.add(new Point(Double.parseDouble(zoneValues[currentValue++].trim()),Double.parseDouble(zoneValues[currentValue++].trim())));
                numVertices--;
            }

            ID = zoneID;
            name = zoneName;
            squad_no = zoneSquad;
            polygonVertices =points;


        }
        catch (Exception ex){

            throw new Exception("Invalid format for Zone in line -'"+line+"' "+ex.getMessage());
        }
    }


    @Override
    public void createObject(Connection con, PreparedStatement stmt) throws SQLException {
        stmt.setLong(1,ID);
        stmt.setString(2,name);
        stmt.setLong(3,squad_no);
        ArrayList<Double> values=  new ArrayList<>();
        String polygon = "POLYGON((";
        for(int i =0;i<polygonVertices.size();i++){
            Point p = polygonVertices.get(i);
            values.add(p.getLongitude());
            polygon+=p.getLongitude();
            polygon+=" ";
            polygon+=p.getLatitude();
                polygon+=",";
            values.add(p.getLatitude());
        }

        polygon+=polygonVertices.get(0).getLongitude();
        polygon+=" ";
        polygon+=polygonVertices.get(0).getLatitude();
       polygon+="))";

        stmt.setString(4,polygon);
        //System.out.println(stmt.toString());
        stmt.execute();




    }

    @Override
    public String toString() {

    return "*Zone: ID:"+ID+" NAME:"+name+" SQUAD NO:"+squad_no+" poly:"+polygonVertices.toString()+"*";

    }

    @Override
    public DBObject getObject(Connection con) throws SQLException {
        return null;
    }


}
