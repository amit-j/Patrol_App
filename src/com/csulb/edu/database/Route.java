package com.csulb.edu.database;

import com.csulb.edu.geometry.Point;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Route implements DBObject {

    long ID;
    List<Point> verrtices;


    public Route(String line) throws Exception {
        try{

            String[] routeValues =line.split(",");

            long routeNo = Long.parseLong(routeValues[0].trim());
            int numVertices = Integer.parseInt(routeValues[1].trim());
            int currentValue =2;

            ArrayList<Point> points = new ArrayList<>();
            while(numVertices>0){
                points.add(new Point(Double.parseDouble(routeValues[currentValue++].trim()),Double.parseDouble(routeValues[currentValue++].trim())));
                numVertices--;
            }

            ID = routeNo;
            verrtices =points;


        }
        catch (Exception ex){

            throw new Exception("Invalid format for Route in line -'"+line+"' "+ex.getMessage());
        }

    }

    @Override
    public void createObject(Connection con, PreparedStatement stmt) throws SQLException {

        stmt.setLong(1,ID);
        String linestring = "LINESTRING(";
        for(int i =0;i<verrtices.size();i++){
            Point p = verrtices.get(i);
            linestring+=p.getLongitude();
            linestring+=" ";
            linestring+=p.getLatitude();
            if(i!=verrtices.size()-1)
                 linestring+=",";
        }

        linestring+=")";

        stmt.setString(2,linestring);
       // System.out.println(stmt.toString());
        stmt.execute();


    }

    @Override
    public String toString(){
        return "*Route: ID:"+ID+" vertices:"+verrtices.toString()+"*";

    }

    @Override
    public DBObject getObject(Connection con) throws SQLException {
        return null;
    }
}
