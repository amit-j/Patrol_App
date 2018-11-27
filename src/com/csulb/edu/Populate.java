package com.csulb.edu;


import com.csulb.edu.database.*;
import com.csulb.edu.helpers.DBObjectFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;

public class Populate {

    private static final String ZONE_TABLE = "Zones";
    private static final String ROUTE_TABLE = "Routes";
    private static final String OFFICER_TABLE = "Officers";
    private static final String INCIDENT_TABLE = "Incidents";



    public static void main(String[] args) {

        try {

            //TODO : read connection details from db.properties file
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PublicSafety?useSSL=true", "amit", "amits2194");
            BufferedReader fileReader;
            String line;
            DBObjectFactory objectFactory = new DBObjectFactory();
            DBObjectType[] objectTypes = DBObjectType.values();
            Class.forName("com.mysql.jdbc.Driver");

            //create prepared statements for the insertion
            HashMap<DBObjectType,PreparedStatement > preparedStatementHashMap = new HashMap<>();
            preparedStatementHashMap.put(DBObjectType.Zone, conn.prepareStatement("INSERT INTO "+ZONE_TABLE+" values (?,?,?,ST_GeomFromText(?))"));
            preparedStatementHashMap.put(DBObjectType.Route, conn.prepareStatement("INSERT INTO "+ROUTE_TABLE+" values (?,ST_GeomFromText(?))"));
            preparedStatementHashMap.put(DBObjectType.Officer, conn.prepareStatement("INSERT INTO "+OFFICER_TABLE+" values (?,?,?,ST_GeomFromText(?))"));
            preparedStatementHashMap.put(DBObjectType.Incident, conn.prepareStatement("INSERT INTO "+INCIDENT_TABLE+" values (?,?,ST_GeomFromText(?))"));

            //truncate all the tables

            Statement stmt = conn.createStatement();
            String query = "TRUNCATE TABLE ";
            stmt.execute(query + ZONE_TABLE);
            stmt.execute(query + ROUTE_TABLE);
            stmt.execute(query + OFFICER_TABLE);
            stmt.execute(query + INCIDENT_TABLE);

            if (args.length == 5) {

                int arg = 1;
                while(arg<5) {
                    String objectType = objectTypes[arg-1].toString();

                    String fileName = args[arg];
                    File file = new File(fileName);

                    if (!file.exists()) {

                        throw new Exception("File not found for "+objectType+" values. Argument given :" + fileName);
                    }

                    fileReader = new BufferedReader(new FileReader(file));
                    while ((line = fileReader.readLine()) != null) {

                        DBObject  obj = objectFactory.getObject(line,objectType);

                        System.out.println("object parsed as "+objectType+":" + obj);
                        obj.createObject(conn, preparedStatementHashMap.get(objectTypes[arg-1]));
                    }

                    fileReader.close();

                    arg++;
                }

            } else {


                System.out.println("we expect 4 parameters :  db.prop zone.txt officer.txt route.txt incident.txt");
            }
        } catch (Exception ex) {
            System.out.println("Something went wrong..");
            ex.printStackTrace();
        }

    }


    enum DBObjectType{
        Zone,
        Route,
        Officer,
        Incident
    }
}
