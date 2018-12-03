package com.csulb.edu.queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PointQuery implements Query {


    private int incident_id;
    private double distance;
    private boolean isExecuted;
    private ResultSet results;

    @Override
    public void parse(String[] args) throws Exception {
        try {

            incident_id = Integer.parseInt(args[1]);
            distance = Double.parseDouble(args[2]);

        } catch (Exception ex) {

            throw new Exception("Error parsing the Point Query. Exception : " + ex);
        }

    }

    @Override
    public ResultSet execute(Connection connection) throws Exception {

        PreparedStatement stmt = connection.prepareStatement("SELECT officer_badge_id," +
                "FLOOR(GCDIST(X(incident_location), Y(incident_location), X(officer_location), Y(officer_location))) as distance," +
                "officer_name " +
                "from Officers," +
                "Incidents" +
                " where  GCDIST(X(incident_location), Y(incident_location), X(officer_location), Y(officer_location)) > ? " +
                " AND incident_id = ? " +
                "order by  GCDIST(X(incident_location), Y(incident_location), X(officer_location), Y(officer_location)) asc");

        stmt.setDouble(1, distance);
        stmt.setInt(2, incident_id);

        results = stmt.executeQuery();
        isExecuted = true;
        return results;
    }

    @Override
    public void displayResults() throws SQLException {

        if (!isExecuted) {
            System.out.println("Please execute the query before you can see the results.");
            return;
        }

        boolean recordsPresent = false;

        while (results.next()) {
            for (int i = 1; i <= 3; i++) {
                recordsPresent = true;
                if (i == 2)
                    System.out.print(results.getString(i)+" ");
                else
                    System.out.print(results.getString(i)+" ");
            }
            System.out.println("");
        }

        if(!recordsPresent)
            System.out.println("No officers found in "+distance+ " of the incident ID:"+incident_id);
    }
}
