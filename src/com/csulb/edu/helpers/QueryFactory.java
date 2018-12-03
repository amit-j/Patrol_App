package com.csulb.edu.helpers;

import com.csulb.edu.database.*;
import com.csulb.edu.queries.*;

public class QueryFactory {

    public Query getObject(String type) throws Exception{

        if(type == null){
            return  null;
        }

        if(type.equalsIgnoreCase("q1"))
            return new RangeQuery();


        if(type.equalsIgnoreCase("q2"))
            return new PointQuery();

        if(type.equalsIgnoreCase("q3"))
            return new FindSquadQuery();

        if(type.equalsIgnoreCase("q4"))
            return new RouteCoverage();




        throw new Exception("Invalid Query type given: "+type);

    }
}
