package com.csulb.edu.helpers;

import com.csulb.edu.database.*;
import com.csulb.edu.queries.Query;
import com.csulb.edu.queries.RangeQuery;

public class QueryFactory {

    public Query getObject(String type) throws Exception{

        if(type == null){
            return  null;
        }

        if(type.equalsIgnoreCase("q1"))
            return new RangeQuery();




        throw new Exception("Invalid Query type given: "+type);

    }
}
