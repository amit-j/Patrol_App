package com.csulb.edu.queries;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Query {


    void parse(String[] args) throws Exception;
    ResultSet execute(Connection connection) throws Exception;
    void displayResults() throws SQLException;



}
