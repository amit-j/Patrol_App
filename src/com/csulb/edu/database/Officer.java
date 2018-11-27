package com.csulb.edu.database;

import com.csulb.edu.geometry.Point;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Officer implements DBObject {

    private long badgeNo;
    private String name;
    private long squadNo;
    private Point location;

    public Officer(String line) throws Exception{
        try {
            String[] officerValues = line.split(",");

            long badgeNo = Long.parseLong(officerValues[0].trim());
            String name = officerValues[1].trim();
            long officerSquad = Long.parseLong(officerValues[2].trim());

            int currentValue = 3;
            Point point = new Point(Double.parseDouble(officerValues[currentValue++].trim()), Double.parseDouble(officerValues[currentValue++].trim()));

            this.badgeNo = badgeNo;
            this.name = name;
            this.location = point;
            this.squadNo = officerSquad;
        }
        catch (Exception ex){
            throw new Exception("Invalid format for Officer in line -'"+line+"' "+ex.getMessage());
        }


    }


    @Override
    public void createObject(Connection con, PreparedStatement stmt) throws SQLException {

        stmt.setLong(1, badgeNo);
        stmt.setString(2, name);
        stmt.setLong(3, squadNo);

        String pointstring = "POINT(";
        Point p = location;
        pointstring += p.getLongitude();
        pointstring += " ";
        pointstring += p.getLatitude();


        pointstring += ")";

        stmt.setString(4, pointstring);
        // System.out.println(stmt.toString());
        stmt.execute();

    }

    @Override
    public String toString(){
        return "*Officer: badge no"+badgeNo+" name:"+name+" squad:"+squadNo+" loc:"+location.toString()+"*";

    }

    @Override
    public DBObject getObject(Connection con) throws SQLException {
        return null;
    }
}
