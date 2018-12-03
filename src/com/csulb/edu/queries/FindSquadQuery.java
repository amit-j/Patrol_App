package com.csulb.edu.queries;

import com.csulb.edu.database.Officer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FindSquadQuery implements Query {

    int squad_no;
    ResultSet results;
    String zoneName;
    boolean isExecuted;


    @Override
    public void parse(String[] args) throws Exception {

        try {

            squad_no = Integer.parseInt(args[1]);

        }
        catch (Exception ex){
            throw new Exception("Error parsing the Find Squad Status Query. Error :"+ex);

        }
    }

    @Override
    public ResultSet execute(Connection connection) throws Exception {


        PreparedStatement stmt = connection.prepareStatement("Select zone_name from Zones where zone_squad_no = ?");
        stmt.setInt(1,squad_no);
        results =stmt.executeQuery();

        if(results.first()){

                zoneName = results.getString(1);

        }

        stmt = connection.prepareStatement("select officer_badge_id,officer_name,\n" +
                "       case\n" +
                "         when ST_CONTAINS(Zones.zone_region, Officers.officer_location) = 1 then 'IN'\n" +
                "         else 'OUT'\n" +
                "         end as status\n" +
                "from Officers,\n" +
                "     Zones\n" +
                "where officer_squad_no =  ? \n"  +
                "  and Zones.zone_squad_no = Officers.officer_squad_no\n" +
                "order by officer_badge_id;");

        stmt.setInt(1, squad_no);

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

        if (!results.first() ) {
            System.out.println("No officers for squad number " + squad_no);
            return;
        }

        System.out.println("Squad "+squad_no+" is now patrolling "+zoneName   );
        while (results.next()) {
            for (int i = 1; i <= 3; i++) {

                    System.out.print(" " + results.getString(i));
            }
            System.out.println("");
        }

    }
}
