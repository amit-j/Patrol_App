package com.csulb.edu.queries;

import com.csulb.edu.geometry.Point;
import com.mysql.jdbc.Connection;

import java.util.List;

public class RangeQuery implements Query {

    List<Point> polygon;


    @Override
    public void parse(String line) throws Exception {

    }


    @Override
    public void execute(Connection connection) throws Exception {

    }
}
