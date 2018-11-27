package com.csulb.edu.database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DBObject {



    //Creates the object with its existing values into the database
    void createObject(Connection con, PreparedStatement stmt) throws SQLException;


    //prints the current object attributes
    String toString();

    //gets the object attributes from the database based on its primary key;
    DBObject getObject(Connection con) throws SQLException;

}
