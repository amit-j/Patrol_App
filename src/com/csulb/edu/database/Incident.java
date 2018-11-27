package com.csulb.edu.database;

import com.csulb.edu.geometry.Point;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Incident implements DBObject {


    private long ID;
    private String type;
    private Point location;


    public Incident(String line) throws Exception{

        try{

            String[] incidentValues =line.split(",");

            long id = Long.parseLong(incidentValues[0].trim());
            String type = incidentValues[1].trim();
            Point p = new Point(Double.parseDouble(incidentValues[2].trim()),Double.parseDouble(incidentValues[3].trim()));
            ID = id;
            this.type =type;
            location = p;


        }
        catch (Exception ex){

            throw new Exception("Invalid format for Incident in line -'"+line+"' "+ex.getMessage());
        }
    }

    @Override
    public void createObject(Connection con, PreparedStatement stmt) throws SQLException {

        stmt.setLong(1, ID);
        stmt.setString(2,type);
        String linestring = "POINT(";

        Point p = location;
        linestring += p.getLongitude();
        linestring += " ";
        linestring += p.getLatitude();

        linestring += ")";

        stmt.setString(3, linestring);
        // System.out.println(stmt.toString());
        stmt.execute();

    }

    @Override
    public String toString(){
        return "*Incident:  ID"+ID+" type:"+type+" loc:"+location.toString()+"*";

    }

    @Override
    public DBObject getObject(Connection con) throws SQLException {
        return null;
    }
}
