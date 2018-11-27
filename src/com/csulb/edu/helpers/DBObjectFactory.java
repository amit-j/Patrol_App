package com.csulb.edu.helpers;

import com.csulb.edu.database.*;

public class DBObjectFactory {


    public DBObject getObject(String line,String type) throws Exception{

        if(type == null){
            return  null;
        }

        if(type.equalsIgnoreCase("ZONE"))
            return new Zone(line);

        if(type.equalsIgnoreCase("ROUTE"))
            return new Route(line);

        if(type.equalsIgnoreCase("OFFICER"))
            return new Officer(line);

        if(type.equalsIgnoreCase("INCIDENT"))
            return new Incident(line);



        return null;

    }
}
