package com.csulb.edu;

import com.csulb.edu.helpers.QueryFactory;
import com.csulb.edu.queries.Query;

import java.sql.Connection;
import java.sql.DriverManager;

public class hw {

    public static void main(String[] args) {
    Query query;
        QueryFactory queryFactory = new QueryFactory();
        try {


            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PublicSafety?useSSL=true", "amit", "amits2194");


            if (args.length > 0) {


                query = queryFactory.getObject(args[0]);
                query.parse(args);
                query.execute(conn);
                query.displayResults();


            } else {
                System.out.println("------------------Usage examples------------------");
                System.out.println("1.Range Query : Returns all the incidents in a certain range. Usage :q1 4 -118.3 34.03 -118.3 34.02 -118.2 34.02 -118.2 34.03");

            }


        } catch (Exception ex) {
            System.out.println("Something went wrong :" + ex);
        }

    }
}
