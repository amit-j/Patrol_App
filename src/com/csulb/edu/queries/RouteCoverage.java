package com.csulb.edu.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RouteCoverage implements Query {

    int routeID;
    ResultSet results;
    boolean isExecuted;

    @Override
    public void parse(String[] args) throws Exception {

        try {
            routeID = Integer.parseInt(args[1]);
        }catch (Exception ex){
            throw new Exception("Error parsing query 3 (Route Coverage) :"+ ex);
        }
    }

    @Override
    public ResultSet execute(Connection connection) throws Exception {

        PreparedStatement stmt = connection.prepareStatement("select zone_id, zone_name from Zones,Routes\n" +
                "where ST_INTERSECTS(zone_region,Routes.route_line) = 0\n" +
                "and route_id = ?");

        stmt.setInt(1,routeID);
        results =stmt.executeQuery();

        isExecuted = true;
        return results;
    }

    @Override
    public void displayResults() throws SQLException {
        if(!isExecuted){
            System.out.println("Please execute the query before you can see the results.");
            return;
        }
        if (!results.first() ) {
            System.out.println("Given route does not exists or does not pass through any of the Zones");
            return;
        }

        while (results.next()) {
            for (int i = 1; i <= 2; i++) {

                System.out.print(" " + results.getString(i));
            }
            System.out.println("");

        }


    }
}
