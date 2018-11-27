package com.csulb.edu.queries;

import com.mysql.jdbc.Connection;

import java.sql.SQLException;

public interface Query {


    void parse(String line) throws Exception;
    void execute(Connection connection) throws Exception;


}
