package com.csulb.edu.queries;

import com.csulb.edu.database.Incident;
import com.csulb.edu.geometry.Point;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//returns the incidents that took place in a given polygon
public class RangeQuery implements Query {

    private List<Point> polygon;
    private boolean isExecuted;
    private ResultSet results;
    private List<Incident> Incidents;


    @Override
    public void parse(String[] args) throws Exception {

        try {

            int n = Integer.parseInt(args[1]);
            int currentValue = 2;
            polygon = new ArrayList<>();
            while (n > 0) {


                polygon.add(new Point(Double.parseDouble(args[currentValue++]), Double.parseDouble(args[currentValue++])));
                n--;
            }




        } catch (Exception ex) {

            throw new Exception("Error in parsing RangeQuery q1. Exception : " + ex);
        }

    }


    @Override
    public ResultSet execute(Connection connection) throws Exception {

        PreparedStatement stmt;
        String poly = "polygon((";
        for (Point p : polygon) {
            poly += p.getLongitude();
            poly += " ";
            poly += p.getLatitude();
            poly += ",";
        }

        poly += polygon.get(0).getLongitude();
        poly += " ";
        poly += polygon.get(0).getLatitude();

        stmt = connection.prepareStatement("select incident_id, ST_AsWKT(incident_location) ,incident_type " +
                "from Incidents where ST_CONTAINS(ST_GeomFromText('" + poly + "))'),incident_location)");


        //System.out.println(stmt.toString());
        results = stmt.executeQuery();
        isExecuted = true;
        return results;

    }

    @Override
    public void displayResults() throws SQLException {
        if (!isExecuted) {
            System.out.println("Execute the query first to get the results");
            return;
        }

        while (results.next()) {
            for (int i = 1; i <= 3; i++) {

                if (i == 2) {

                   String point = results.getString(i).replaceAll("POINT", "").replaceAll("\\)", "").replaceAll("\\(", "") + " ";
                   String[] split = point.split(" ");
                   System.out.print (split[1]+ ","+split[0]+" ");
                }

                else
                    System.out.print(results.getString(i) + " ");
            }
            System.out.println("");
        }

    }


}
